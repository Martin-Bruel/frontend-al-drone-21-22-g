#!/bin/bash
#Build and run services in docker
#Run the pytest
cd ci
behave launchDelivery.feature
