#!/bin/bash

echo "Building Docker images..."
docker-compose build

echo "Starting services..."
docker-compose -p ads up