#!/usr/bin/env bash

echo "Stop app; start" > /tmp/deployment.log
service tomcat8 stop >> /tmp/deployment.log
echo "Stop app; stop" > /tmp/deployment.log