package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.*;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.DroneRegistration;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PackageRegistration;
import com.polytech.si5.al.dronedelivery.team.g.truck.repositories.DeliveryRepository;
import com.polytech.si5.al.dronedelivery.team.g.truck.repositories.DroneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class SchedulerBeanTest {

    @Autowired private DeliveryRepository deliveryRepository;
    @Autowired private DroneRepository droneRepository;

    @BeforeEach
    public void clearData() {
        deliveryRepository.deleteAll();
        droneRepository.deleteAll();
    }

    @Autowired private DroneRegistration droneRegistration;
    @Autowired private PackageRegistration packageRegistration;

    List<Drone> drones;
    List<Delivery> packages;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Resource
    private SchedulerBean allocationProvider;

    @BeforeEach
    public void setUp() throws Exception {
        drones = new ArrayList<>();
        packages = new ArrayList<>();
    }

    @AfterEach
    public void cleanup() {
        for (Drone d : drones) {
            d = entityManager.merge(d);
            entityManager.remove(d);
        }
        drones.clear();
        
        for (Delivery d : packages) {
            d = entityManager.merge(d);
            entityManager.remove(d);
        }
        packages.clear();
    }

    @Test
    public void schedulingNotEmptyTest() {
        Drone drone = new Drone("drone", new ConnectionInterface("localhost","8080"), 1);
        drones.add(drone);
        droneRegistration.registerDrone(drone);

        Delivery delivery = new Delivery(new Position(0,0));
        packages.add(delivery);
        packageRegistration.registerDelivery(delivery);

        List<Allocation> allocations = allocationProvider.getAllocations();
        assertThat(allocations).isNotEmpty();
    }

    @Test
    public void schedulingAssignsDronesToPackagesTest() {
        Drone drone = new Drone("drone", new ConnectionInterface("localhost","8080"), 1);
        drones.add(drone);
        droneRegistration.registerDrone(drone);

        Delivery delivery = new Delivery(new Position(0,0));
        packages.add(delivery);
        packageRegistration.registerDelivery(delivery);

        List<Allocation> allocations = allocationProvider.getAllocations();
        Allocation allocation = allocations.get(0);
        Drone allocatedDrone = allocation.getDrone();
        Delivery allocatedDelivery = allocation.getDeliveries().get(0);

        assertTrue(allocatedDrone.getDeliveries().contains(allocatedDelivery));
        assertEquals(allocatedDrone, allocatedDelivery.getDeliveryDrone());
    }

    @Test
    public void schedulingAssignsAllPackagesTest() {
        Drone drone = new Drone("drone", new ConnectionInterface("localhost","8080"), 1);
        drones.add(drone);
        droneRegistration.registerDrone(drone);

        Drone bigDrone = new Drone("bigDrone", new ConnectionInterface("localhost","8080"), 3);
        drones.add(bigDrone);
        droneRegistration.registerDrone(bigDrone);

        int nb_deliveries = 4;
        for (int i=0; i<nb_deliveries; i++) {
            Delivery delivery = new Delivery(new Position(0,i));
            packages.add(delivery);
            packageRegistration.registerDelivery(delivery);
        }

        List<Allocation> allocations = allocationProvider.getAllocations();
        assertEquals(2, allocations.size());

        Allocation allocation1 = allocationProvider.getAllocations().get(0); // gros drone en priorité
        assertEquals(bigDrone, allocation1.getDrone());
        assertEquals(3, allocation1.getDeliveries().size());

        Allocation allocation2 = allocationProvider.getAllocations().get(1); // gros drone en priorité
        assertEquals(drone, allocation2.getDrone());
        assertEquals(1, allocation2.getDeliveries().size());

    }

}