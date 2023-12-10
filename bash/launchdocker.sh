#!/bin/bash

cd ..
mvn clean package -DskipTests

cd docker || exit
docker-compose up --force-recreate --build