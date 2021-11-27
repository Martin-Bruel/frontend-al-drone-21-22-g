package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.constants.DeliveryStatusCode;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Position;
import com.polytech.si5.al.dronedelivery.team.g.truck.enumeration.DeliveryStatus;
import com.polytech.si5.al.dronedelivery.team.g.truck.repositories.DeliveryRepository;
import com.polytech.si5.al.dronedelivery.team.g.truck.repositories.DroneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DeliveryTrackerTest {

    @Autowired
    DeliveryTracker deliveryTracker;

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager.clear();
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    @Transactional
    void updateDeliverySate() {
        Drone drone=new Drone();
        entityManager.persist(drone);
        Delivery deliveryA = new Delivery(new Position());
        Delivery deliveryB = new Delivery(new Position());

        entityManager.persist(deliveryA);
        entityManager.persist(deliveryB);
        drone =entityManager.merge(drone);
        drone.getDeliveries().add(deliveryA);
        drone.getDeliveries().add(deliveryB);
        deliveryA.setDeliveryDrone(drone);
        deliveryB.setDeliveryDrone(drone);
        deliveryTracker.setMustSendMessage(false);
        deliveryTracker.updateDeliverySate(drone.getId(), DeliveryStatusCode.PACKAGE_DELIVERED, deliveryA.getId());
        deliveryTracker.updateDeliverySate(drone.getId(), DeliveryStatusCode.PACKAGE_DELIVERED, deliveryB.getId());
        assertEquals(deliveryA.getDeliveryStatus(), DeliveryStatus.DELIVERED);
        assertEquals(deliveryB.getDeliveryStatus(), DeliveryStatus.DELIVERED);
    }

    @Test
    void sendNotification() {
    }
}
