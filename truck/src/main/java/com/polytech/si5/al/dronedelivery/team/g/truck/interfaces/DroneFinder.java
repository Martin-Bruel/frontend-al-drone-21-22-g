package com.polytech.si5.al.dronedelivery.team.g.truck.interfaces;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;

import java.util.List;

public interface DroneFinder {

    Drone findDroneById(Long droneId);
    List<Drone> getAvailableDrones();
    List<Drone> getAllDrones();
}
