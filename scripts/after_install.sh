#!/usr/bin/env bash

echo "After install; start" >> /tmp/deployment.log

function cleanup {
    local exit_code=$1
    echo "Exit code: "${exit_code} >> /tmp/deployment.log

    if [ -d /tmp/urujdas-deployment ]; then
        rm -rf /tmp/urujdas-deployment >> /tmp/deployment.log
    fi

    echo "After install; stop" >> /tmp/deployment.log
    exit ${exit_code}
}


pushd /tmp/urujdas-deployment/target >> /tmp/deployment.log

tar -xf deployment.tar.gz >> /tmp/deployment.log
pushd deployment/flyway >> /tmp/deployment.log

source /etc/profile.d/export_variables.sh >> /tmp/deployment.log

echo "Migrating" >> /tmp/deployment.log
./flyway -driver=org.postgresql.Driver \
    -url=jdbc:postgresql://${DB_HOSTNAME}/${DB_DATABASE} \
    -user=${DB_USER} \
    -password=${DB_PASSWORD} \
    -schemas=urujdas \
    -locations=filesystem:deltas \
    -jarDirs=drivers \
    migrate >> /tmp/deployment.log

if [ $? -ne 0 ]; then
    cleanup 1
fi

echo "After migrate" >> /tmp/deployment.log

popd >> /tmp/deployment.log

cp urujdas-*.war /usr/share/tomcat8/webapps/ROOT.war >> /tmp/deployment.log

if [ $? -ne 0 ]; then
    cleanup 1
fi

popd

cleanup 0