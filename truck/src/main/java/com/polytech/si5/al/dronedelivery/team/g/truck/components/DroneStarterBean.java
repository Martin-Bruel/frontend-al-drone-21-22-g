package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.FlightPlan;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Position;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.*;
import com.polytech.si5.al.dronedelivery.team.g.truck.services.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DroneStarterBean implements DroneLauncher {

    @Autowired
    PositionProvider positionProvider;

    @Autowired
    PackageFinder packageFinder;

    @Autowired
    PathFinder pathFinder;

    @Autowired
    DroneFinder droneFinder;

    @Autowired
    DroneService droneService;

    Logger logger = LoggerFactory.getLogger(DroneStarterBean.class);


    @Override
    public void start(Long droneId, Long[] packageIds) {
        logger.info("Starting drone " + droneId + " with package " + packageIds);
        Position truckPos = positionProvider.getTruckPosition();
        List<Delivery> deliveries = new ArrayList<>();
        for(Long id : packageIds){
            deliveries.add(packageFinder.getPackageByPackageId(id));
        }

        FlightPlan flightPlan = pathFinder.getPath(truckPos, deliveries);
        Drone drone = droneFinder.findDroneById(droneId);
        droneService.launchDrone(flightPlan, drone);
    }
}
