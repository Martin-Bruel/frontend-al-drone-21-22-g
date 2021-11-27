package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.DroneStatus;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.DroneFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.repositories.DroneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DroneRegistryBeanTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    DroneFinder droneFinder;

    private List<Drone> managedDrones;

    @BeforeEach
    void setUp() throws Exception {
        entityManager.clear();
        managedDrones = new ArrayList<>();
    }

    @AfterEach
    public void cleanup() {
        for (Drone d : managedDrones) {
            d = entityManager.merge(d);
            entityManager.remove(d);
        }
    }

    @Test
    @Transactional
    public void getAvailableDronesTest() {
        Drone a = new Drone(); a.setStatus(DroneStatus.FLYING_TO_DELIVERY);
        Drone b = new Drone(); b.setStatus(DroneStatus.FLYING_TO_TRUCK);
        Drone c = new Drone(); c.setStatus(DroneStatus.LOST);
        Drone d = new Drone(); d.setStatus(DroneStatus.READY);

        entityManager.persist(a); managedDrones.add(a);
        entityManager.persist(b); managedDrones.add(b);
        entityManager.persist(c); managedDrones.add(c);
        entityManager.persist(d); managedDrones.add(d);

        entityManager.flush();

        List<Drone> drones = droneFinder.getAvailableDrones();
        assertTrue(drones.contains(d));
        assertFalse(drones.contains(c));
    }

}
