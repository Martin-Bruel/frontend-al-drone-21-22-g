#!/bin/bash
tGreen="$(tput setaf 2)"
tBlue="$(tput setaf 4)"
tNC="$(tput sgr0)"


#Preparing environment
echo  "${tBlue}Building truck application${tNC}"
cd ../truck
mvn clean package -DskipTests

# Building the docker image
echo  "${tBlue}Building truck docker${tNC}"
docker build -t delivery-drone/truck .

# Building the docker image
echo  "${tGreen}Building drone docker${tNC}"
cd ../drone
docker build -t delivery-drone/drone .

# Building the docker image
echo  "${tGreen}Building warehouse docker${tNC}"
cd ../warehouse
docker build -t delivery-drone/warehouse .
