#!/usr/bin/env bash

echo "Validate; start" >> /tmp/deployment.log
#Timeout needed for service to startup
retries=5

result=0
for i in $(seq 1 ${retries}); do
    response=$(curl --write-out %{http_code} --silent --output /dev/null localhost:8080/v1/healthcheck)
    if [ ${response} -ne '200' ]; then
        result=1
        echo 'Response: '${response} >> /tmp/deployment.log
    fi

    sleep 10
done

echo "Validate; stop" >> /tmp/deployment.log
exit ${result}
