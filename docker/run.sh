#!/bin/bash

echo "<<" Cleaning the database and the database client .. ">>"
echo
docker-compose -f ./docker-compose.yml rm
echo
echo Done.
echo
echo "<<" Starting the drone projet ">>"
echo
docker-compose -f ./docker-compose.yml up -d --no-build
echo
echo Done.
