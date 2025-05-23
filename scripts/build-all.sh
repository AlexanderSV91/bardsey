#!/bin/bash

#VERSION=3.4.5 ./scripts/build-all.sh

SERVICES=(
    "config-server"
    "discovery-server"
    "api-gateway"
    "url-shortener-service"
)

build_docker_image() {
    local service_name=$1
    local version=${VERSION:-"latest"}
#    docker build . -f ./"${service_name}"/Dockerfile -t "${service_name}:${version}"
    docker build ./"${service_name}" -f ./"${service_name}"/Dockerfile -t "${service_name}:${version}"
}

for service in "${SERVICES[@]}"; do
    build_docker_image "$service"
done
