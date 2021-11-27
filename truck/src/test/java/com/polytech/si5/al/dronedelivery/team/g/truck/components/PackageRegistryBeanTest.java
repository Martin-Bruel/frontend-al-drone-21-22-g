package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.*;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PackageFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PackageRegistryBeanTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PackageFinder packageFinder;

    Drone drone;
    Delivery delivery;

    @BeforeEach
    public void setUp() {
        drone = new Drone("droneName", new ConnectionInterface(),1);
        delivery = new Delivery(new Position(2,2));
    }

    @Test
    @Transactional
    void getPackagesByDroneId() {
        delivery = entityManager.merge(delivery);
        drone = entityManager.merge(drone);
        delivery.setDeliveryDrone(drone);

        entityManager.persist(delivery);
        entityManager.persist(drone);

        List<Delivery> deliveryResult = packageFinder.getPackagesByDroneId(drone.getId());
        assertEquals(delivery, deliveryResult.get(0));
    }


}