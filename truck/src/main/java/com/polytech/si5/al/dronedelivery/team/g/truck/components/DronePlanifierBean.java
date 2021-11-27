package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.FlightPlan;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Position;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Step;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PathFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.utils.PositionCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DronePlanifierBean implements PathFinder {

    Logger logger = LoggerFactory.getLogger(DronePlanifierBean.class);

    @Override
    public FlightPlan getPath(Position truckPos, List<Delivery> deliveries) {
        logger.info("Determine flight plan");
        List<Step> deliverySteps = new ArrayList<>();
        deliveries.sort((d1, d2) -> {
            if (PositionCalculator.distance(d1.getPosition(), truckPos) == PositionCalculator.distance(d2.getPosition(), truckPos))
                return 0;
            else return PositionCalculator.distance(d1.getPosition(), truckPos) > PositionCalculator.distance(d2.getPosition(), truckPos) ? 1 : -1;
        });
        deliveries.forEach(delivery -> deliverySteps.add(new Step(delivery.getPosition(), delivery.getId())));
        return new FlightPlan(deliverySteps, truckPos);
    }
}
