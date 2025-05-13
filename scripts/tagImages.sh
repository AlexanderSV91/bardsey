#!/bin/bash

SERVICES=(
    "config-server"
    "discovery-server"
    "api-gateway"
    "url-shortener-service"
)

tag_docker_image() {
    local service_name=$1
    local version=${VERSION:-"latest"}
    docker tag "${REPOSITORY_PREFIX}/${service_name}" "${REPOSITORY_PREFIX}/${service_name}:${version}"
}

for service in "${SERVICES[@]}"; do
    tag_docker_image "$service"
done
