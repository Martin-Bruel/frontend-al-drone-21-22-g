package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.FlightPlan;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Position;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PathFinder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DronePlanifierBeanTest {

    @Autowired
    private PathFinder pathFinder;

    @Test
    void getPathTest(){
        List<Delivery> deliveries = new ArrayList<>();
        deliveries.add(new Delivery(new Position(2,2), 0L));
        Position truckPos= new Position(0,0);
        FlightPlan flightPlan = pathFinder.getPath(truckPos, deliveries);
        System.out.println(flightPlan);
        assertEquals(flightPlan.getSteps().get(flightPlan.getSteps().size() - 1).getDeliveryId(), deliveries.get(0).getId());
    }

    @Test
    void getPathSeveralPackageTest(){
        List<Delivery> deliveries = new ArrayList<>();

        Delivery d1 = new Delivery(new Position(2,2), 0L);
        Delivery d2 = new Delivery(new Position(2,3), 0L);
        Delivery d3 = new Delivery(new Position(2,4), 0L);

        deliveries.add(d2);
        deliveries.add(d3);
        deliveries.add(d1);

        Position truckPos= new Position(0,0);
        FlightPlan flightPlan = pathFinder.getPath(truckPos, deliveries);
        assertEquals(flightPlan.getSteps().get(flightPlan.getSteps().size() - 1).getDeliveryId(), d3.getId());
        assertEquals(flightPlan.getSteps().get(flightPlan.getSteps().size() - 2).getDeliveryId(), d2.getId());
        assertEquals(flightPlan.getSteps().get(flightPlan.getSteps().size() - 3).getDeliveryId(), d1.getId());
    }
}
