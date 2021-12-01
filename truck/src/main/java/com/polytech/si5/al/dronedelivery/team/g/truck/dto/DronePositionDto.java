package com.polytech.si5.al.dronedelivery.team.g.truck.dto;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Position;

import java.io.Serializable;

public class DronePositionDto implements Serializable {

    Position position;
    Long droneId;

    public DronePositionDto(Position position, Long droneId) {
        this.position = position;
        this.droneId = droneId;
    }

    public Position getPosition() {
        return position;
    }

    public Long getDroneId() {
        return droneId;
    }
}
