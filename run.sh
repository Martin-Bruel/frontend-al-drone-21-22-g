#Build and run services in docker
#Run the pytest
cd ci
behave launchDelivery.feature
cd ..

echo
read -p "Press any key to continue... " -n1 -s
echo
#Cleaning execution
cd docker
echo "<<" Stop drone projet ">>"
./stop.sh
cd ..
