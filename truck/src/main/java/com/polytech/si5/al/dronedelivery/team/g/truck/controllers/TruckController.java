package com.polytech.si5.al.dronedelivery.team.g.truck.controllers;

import com.polytech.si5.al.dronedelivery.team.g.truck.components.TruckLocatorBean;
import com.polytech.si5.al.dronedelivery.team.g.truck.dto.TruckInfoDto;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.DroneFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PackageFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PositionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TruckController {

    @Autowired
    PositionProvider positionProvider;

    @Autowired
    DroneFinder droneFinder;

    @Autowired
    PackageFinder packageFinder;

    @GetMapping("truck/info")
    public TruckInfoDto getTruckInfo(){
        return new TruckInfoDto(positionProvider.getTruckPosition(), packageFinder.getAllDeliveries(), droneFinder.getAllDrones());
    }

}
