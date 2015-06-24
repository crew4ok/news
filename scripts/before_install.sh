#!/usr/bin/env bash

# Delete old application version
echo "Before install; start" >> /tmp/deployment.log

rm -rf /usr/share/tomcat8/webapps/ROOT >> /tmp/deployment.log
ls /usr/share/tomcat8/webapps >> /tmp/deployment.log

if [ $? -ne 0 ]; then
    echo "Before install failed" >> /tmp/deployment.log
    exit 1
fi

echo "Before install; end" >> /tmp/deployment.log