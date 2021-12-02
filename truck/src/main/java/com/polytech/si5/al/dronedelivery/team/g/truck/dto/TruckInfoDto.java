package com.polytech.si5.al.dronedelivery.team.g.truck.dto;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Position;

import java.util.List;

public class TruckInfoDto {

    Position truckPosition;
    List<Delivery> deliveries;
    List<Drone> drones;

    public TruckInfoDto(Position truckPosition, List<Delivery> deliveries, List<Drone> drones) {
        this.truckPosition = truckPosition;
        this.deliveries = deliveries;
        this.drones = drones;
    }

    public Position getTruckPosition() {
        return truckPosition;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public List<Drone> getDrones() {
        return drones;
    }

    @Override
    public String toString() {
        return "TruckInfoDto{" +
                "truckPosition=" + truckPosition +
                ", deliveries=" + deliveries +
                ", drones=" + drones +
                '}';
    }
}
