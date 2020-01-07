#!/bin/bash

# http method, refer to: https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Methods
httpMethodArray=("GET" "HEAD" "CONNECT" "DELETE" "OPTIONS" "PATCH" "POST" "PUT" "TRACE")

readRequest() {
	bmcIp=""
	bmcPath="/"
	firstLine=""
	while true
	do
		read line
		reqKey=`echo $line | awk -F '[ :]' '{gsub("\r","");print$1}'`
		reqKey="$(echo $reqKey | tr '[:lower:]' '[:upper:]')"

		if [ -z "${firstLine}" ]; then
			for method in ${httpMethodArray[@]}
			do
				[ "${reqKey}" = "$method" ] && firstLine="true" &&
				[ "${reqKey}" = "GET" -o "${reqKey}" = "HEAD" ] &&
				bmcPath=`echo $line | awk '{print$2}'`
			done
		fi

		if [ -z "${firstLine}" ]; then
			break
		else
			if [ -z "${reqKey}" ]; then
				break
			fi

			if [ "${reqKey}" = "HOST" ]; then
				bmcIp=`echo $line | awk -F : '{gsub(" ","");print$2}'`
			fi
		fi
	done
}

sendRespData() {
	cat <<-EOF
	HTTP/1.1 308 Permanent Redirect
	Location: https://$bmcIp$bmcPath
	Content-Length: 23
	Content-Type: text/html; charset=UTF-8
	X_Frame_Options: DENY
	Pragma: no-cache
	Cache_Control: no-Store,no-Cache
	X-XSS-Protection: 1; mode=block
	X-Content-Type-Options: nosniff
	Connection: close

	308 Permanent Redirect
	EOF
}

main() {
	while true
	do
		coproc nc -l -p 80
		{
			readRequest
			if [ -n "${bmcIp}" ]; then
				sendRespData
			fi
		} <&${COPROC[0]} >&${COPROC[1]}

		# When code runs here it means the above functions are finished.
		# Usually nc will quit after sending the HTTP response and if the client behaves well.
		# But when networks is not stable, the resposne may not be fully sent,
		# and we should wait for a while before killing nc.
		sleep 0.1
		[ -n "$COPROC_PID" ] && echo "killing $COPROC_PID" && kill "$COPROC_PID"
    done
}

main
