#!/bin/sh

if [ ! -f $CERT ]; then
    openssl req -x509 -sha256 -newkey rsa:2048 -keyout $CERT -out $CERT \
    -days 3650 -subj "/O=openbmc-project.xyz/CN=localhost" \
    -nodes
fi
