#!/usr/bin/env just --justfile

# Bring resources for local development
local:
  docker-compose down
  docker-compose -p ninja-backend up ninja-db ninja-cache

# Run full backend via Docker
run:
  docker-compose down
  docker rmi -f ninja/backend:latest
  docker-compose -p ninja-backend up

# Run tests
test:
  ./gradlew clean test