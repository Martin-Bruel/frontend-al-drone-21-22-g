package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Position;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PositionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TruckLocatorBean implements PositionProvider {

    Logger logger = LoggerFactory.getLogger(TruckLocatorBean.class);

    @Override
    public Position getTruckPosition() {
        logger.info("Get truck location");
        return new Position(0,0);
    }
}
