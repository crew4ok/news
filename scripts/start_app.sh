#!/usr/bin/env bash

echo "Start app; start" >> /tmp/deployment.log
service tomcat8 start >> /tmp/deployment.log
echo "Start app; stop" >> /tmp/deployment.log