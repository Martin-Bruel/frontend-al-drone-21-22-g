package com.polytech.si5.al.dronedelivery.team.g.truck.interfaces;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import com.polytech.si5.al.dronedelivery.team.g.truck.enumeration.DeliveryStatus;

public interface PackageModifier {
    void setPackageStatus(Delivery delivery, DeliveryStatus status);
    void updateDeliveryDrone(Drone drone, Delivery delivery);
}
