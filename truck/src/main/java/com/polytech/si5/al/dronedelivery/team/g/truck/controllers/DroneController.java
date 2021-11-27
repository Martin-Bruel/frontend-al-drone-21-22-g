package com.polytech.si5.al.dronedelivery.team.g.truck.controllers;

import com.polytech.si5.al.dronedelivery.team.g.truck.dto.DeliveryStateDto;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import com.polytech.si5.al.dronedelivery.team.g.truck.dto.DroneDto;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.DeliveryStateNotifier;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.DroneFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.DroneRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class DroneController {

    @Autowired
    private DroneFinder droneFinder;

    Logger logger = LoggerFactory.getLogger(DroneController.class);

    @Autowired
    DroneRegistration droneRegistration;

    @Autowired
    private DeliveryStateNotifier deliveryStateNotifier;

    @RequestMapping(value="/drones", method= RequestMethod.GET)
    public List<Drone> getAllDrones() {
        return droneFinder.getAllDrones();
    }

    @PostMapping(value = "/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> post(@RequestBody DeliveryStateDto deliveryStateDto) {
        deliveryStateNotifier.updateDeliverySate(deliveryStateDto.getDroneId(), deliveryStateDto.getDeliveryState(), deliveryStateDto.getDeliveryId());
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @GetMapping(value="/drones/{id}")
    public Drone getDrone(@PathVariable Long id) {
        try{
            return droneFinder.findDroneById(id);
        }
        catch (IllegalArgumentException iae){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone with id " + id + " do not exist...");
        }
    }

    @PostMapping("/truck-api/delivery/status")
    public void getDeliveryStatus(){
        logger.info("receive start demand");
    }

    @PostMapping("/connect/drone")
    public Long connect(@RequestBody DroneDto droneDto){
        logger.info("new drone connected : " + droneDto.name + " - " + droneDto.host + ":" + droneDto.port);
        return droneRegistration.registerDrone(new Drone(droneDto));
    }
}
