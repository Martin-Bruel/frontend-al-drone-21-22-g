package com.polytech.si5.al.dronedelivery.team.g.truck.views;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Allocation;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AllocationView {

    private Long droneId;
    private List<Long> deliveryIds;

    public AllocationView(Allocation a) {
        this.droneId = a.getDrone().getId();
        this.deliveryIds = a.getDeliveries().stream()
                .map(Delivery::getId)
                .collect(Collectors.toList());
    }

}
