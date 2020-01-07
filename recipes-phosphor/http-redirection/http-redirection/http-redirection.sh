#!/bin/bash

bmcIp=""
bmcPath="/"

readRequest() {
	while true
	do
		read line
		reqKey=`echo $line | awk -F '[ :]' '{print$1}' | sed $'s/\r//'`
		if [ -z "${reqKey}" ]; then
			break
		fi

		if [ "${reqKey}" == "GET" ]; then
			bmcPath=`echo $line | awk '{print$2}'`
		fi

		if [ "${reqKey}" == "POST" ]; then
			bmcPath=""
		fi

		if [ "${reqKey}" == "Host" ]; then
			bmcIp=`echo $line | awk -F : '{print$2}' | awk '{gsub(/^\s+|\s+$/, "");print}'`
		fi
	done

	if [ -z "$bmcIp" ]; then
		exit -2
	fi
}

sendRespData() {
	echo "HTTP/1.1 308 Permanent Redirect"
	echo "Content-Length: 23"
	echo "Location: https://$bmcIp$bmcPath"
	echo "Content-Type: text/html; charset=UTF-8"
	echo "X_Frame_Options: DENY"
	echo "Pragma: no-cache"
	echo "Cache_Control: no-Store,no-Cache"
	echo "X-XSS-Protection: 1; mode=block"
	echo "X-Content-Type-Options: nosniff"
	echo ""
	echo "308 Permanent Redirect"
}

main() {
	while true
	do
		coproc nc -l -p 80
		{
			readRequest
			sendRespData
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
