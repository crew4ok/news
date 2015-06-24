#!/usr/bin/env bash

# Delete old application version
rm -rf /usr/share/tomcat8/webapps/ROOT

if [ $? -ne 0 ]; then
    echo "Before install failed"
    exit 1
fi