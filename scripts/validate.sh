#!/usr/bin/env bash

#Timeout needed for service to startup
retries=5

result=0
for i in {1..${retries}}; do
    sleep 10

    response=$(curl --write-out %{http_code} --silent --output /dev/null localhost:8080/v1/healthcheck)
    if [ ${response} -ne '200' ]; then
        result=1
        echo 'Response: '${response}
    fi
done

exit ${result}