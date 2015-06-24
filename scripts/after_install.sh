#!/usr/bin/env bash

function cleanup {
    local exit_code=$1
    echo "Exit code: "${exit_code}

#    if [ -d /tmp/urujdas-deployment ]; then
#        rm -rf /tmp/urujdas-deployment
#    fi

    exit ${exit_code}
}


pushd /tmp/urujdas-deployment/target

tar -xf deployment.tar.gz
pushd deployment/flyway

source /root/export_variables.sh

echo "Migrating"
./flyway -driver=org.postgresql.Driver \
    -url=jdbc:postgresql://${DB_HOSTNAME}/${DB_DATABASE} \
    -user=${DB_USER} \
    -password=${DB_PASSWORD} \
    -schemas=urujdas \
    -locations=filesystem:deltas \
    -jarDirs=drivers \
    migrate

if [ $? -ne 0 ]; then
    cleanup 1
fi

echo "After migrate"

popd

cp urujdas-*.war /usr/share/tomcat8/webapps/ROOT.war

if [ $? -ne 0 ]; then
    cleanup 1
fi

popd

cleanup 0