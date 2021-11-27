package com.polytech.si5.al.dronedelivery.team.g.truck.entities;

import com.polytech.si5.al.dronedelivery.team.g.truck.views.AllocationView;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Allocation {

    private final Drone drone;
    private final List<Delivery> deliveries;

    public Allocation(Drone drone, List<Delivery> deliveries) {
        this.drone = drone;
        this.deliveries = deliveries;
    }

    @Override
    public String toString() {
        List<String> deliveryIds = deliveries.stream()
                .map(Delivery::getId)
                .map(Object::toString)
                .collect(Collectors.toList());
        return "Position{" +
                "drone=" + drone.getId() +
                ", delivery=" + String.join(",", deliveryIds)
                + '}';
    }

    public AllocationView getView() {
        return new AllocationView(this);
    }
}
