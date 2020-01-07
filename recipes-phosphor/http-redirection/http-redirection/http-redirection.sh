#!/bin/bash

readRequest() {
	bmcIp=""
	bmcPath="/"
	while true
	do
		read line
		reqKey=`echo $line | awk -F '[ :]' '{print$1}' | sed $'s/\r//'`
		reqKey="$(echo $reqKey | tr '[:lower:]' '[:upper:]')"

		if [ -z "${reqKey}" ]; then
			break
		fi

		if  [ "${reqKey}" == "GET" -o "${reqKey}" == "HEAD" ]; then
			bmcPath=`echo $line | awk '{print$2}'`
		fi

		if [ "${reqKey}" == "HOST" ]; then
			bmcIp=`echo $line | awk -F : '{print$2}' | awk '{gsub(/^\s+|\s+$/, "");print}'`
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
	Connection: close to HTTP/1.1

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
