package com.polytech.si5.al.dronedelivery.team.g.truck.interfaces;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;

import java.util.List;

public interface PackageFinder {

    List<Delivery> getAllDeliveries();
    List<Delivery> getDeliverablePackages();
    Delivery getPackageByPackageId(Long packageId);
    List<Delivery> getPackagesByDroneId(Long droneId);
}
