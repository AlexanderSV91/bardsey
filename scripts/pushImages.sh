#!/bin/bash

SERVICES=(
    "config-server"
    "discovery-server"
    "api-gateway"
    "url-shortener-service"
)

push_docker_image() {
    local service=$1
    local repo_prefix=${REPOSITORY_PREFIX:-"default-repo"}  # Provide default if not set
    local version=${VERSION:-"latest"}                      # Provide default if not set

    echo "Pushing ${service} image..."
    if docker push "${repo_prefix}/${service}:${version}"; then
        echo "Successfully pushed ${service}"
    else
        echo "Failed to push ${service}"
        return 1
    fi
}

# Validate required environment variables
if [[ -z "${REPOSITORY_PREFIX}" ]]; then
    echo "Error: REPOSITORY_PREFIX environment variable is not set"
    exit 1
fi

for service in "${SERVICES[@]}"; do
    push_docker_image "$service"
done
