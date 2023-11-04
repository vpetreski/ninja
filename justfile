#!/usr/bin/env just --justfile

# Bring DB for local development
local:
  docker-compose down
  docker-compose -p ninja-backend up ninja-db

# Run full backend via Docker
run:
  docker-compose down
  docker rmi -f ninja/backend:latest
  docker-compose -p ninja-backend up

test:
  ./gradlew clean test