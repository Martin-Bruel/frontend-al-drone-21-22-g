package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Allocation;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Position;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.*;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class SchedulerBean implements AllocationProvider {

    Logger logger = LoggerFactory.getLogger(SchedulerBean.class);


    @Autowired
    private PackageSelector packageSelector;
    @Autowired
    private DroneModifier droneModifier;
    @Autowired
    private DroneFinder droneFinder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Allocation> getAllocations() {
        logger.info("Get allocations");
        List<Delivery> deliveries = packageSelector.getDeliverySelected();
        List<Drone> drones = droneFinder.getAvailableDrones();

        List<Allocation> allocations = new ArrayList<>();

        drones.sort(Comparator.comparingInt(Drone::getCapacity).reversed());

        for (Drone drone : drones) {
            int amount = Math.min(deliveries.size(), drone.getCapacity());
            List<Delivery> selection = new ArrayList<>(deliveries.subList(0,amount));
            deliveries.removeAll(selection);
            if(!selection.isEmpty()) allocations.add(new Allocation(drone,selection));
        }

        for (Allocation a : allocations) {
            Drone drone = entityManager.merge(a.getDrone());
            Hibernate.initialize(drone.getDeliveries());

            List<Delivery> deliveryList = new ArrayList<>();
            for (Delivery d : a.getDeliveries()) {
                Delivery merged = entityManager.merge(d);
                Hibernate.initialize(merged.getDeliveryDrone());
                deliveryList.add(merged);
            }
            droneModifier.assignDeliveryToDrone(drone,deliveryList);
        }

        return allocations;
    }
}
