package com.polytech.si5.al.dronedelivery.team.g.truck.controllers;

import com.polytech.si5.al.dronedelivery.team.g.truck.dto.DeliveryDto;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PackageFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PackageRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PackageController {

    @Autowired
    PackageRegistration packageRegistration;

    @Autowired
    PackageFinder packageFinder;

    @PostMapping("/package/add")
    public void addPackages(@RequestBody DeliveryDto deliveryDto){
        packageRegistration.registerDelivery(new Delivery(deliveryDto));
    }

    @GetMapping("/packages/{id}")
    public Delivery getPackages(@PathVariable Long id){
        return packageFinder.getPackageByPackageId(id);
    }


}
