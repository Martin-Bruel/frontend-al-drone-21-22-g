#!/bin/bash

# Don't change!
Red="\033[1;31m"
Green="\033[1;32m"
Yellow="\033[1;33m"
Purple="\033[1;35m"
Cyan="\033[1;36m"
NC="\033[0m"

tBalck="$(tput setaf 0)"
tRed="$(tput setaf 1)"
tGreen="$(tput setaf 2)"
tYellow="$(tput setaf 3)"
tBlue="$(tput setaf 4)"
tPurple="$(tput setaf 5)"
tCyan="$(tput setaf 6)"
tWhite="$(tput setaf 7)"
tWhiteBackground="$(tput setab 7)"
tNC="$(tput sgr0)"

#Build and run services in docker
cd docker
./build.sh
./run.sh
cd ..

echo
echo "<<" Install python requirement ">>"

#Install python requirement
cd ci
python -m pip install -r requirements.txt
cd ..

echo "${tPurple}Prepare Done !${tNC}"
