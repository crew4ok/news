#!/usr/bin/env bash

response=$(curl --write-out %{http_code} --silent --output /dev/null servername)
if [ ${response} -eq '200' ]; then
    exit 0
else
    exit 1
fi
