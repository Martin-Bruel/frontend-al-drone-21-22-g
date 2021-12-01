package com.polytech.si5.al.dronedelivery.team.g.truck.dto;

import lombok.Getter;

@Getter
public class PositionDto {
    private double latitude;
    private double longitude;

    @Override
    public String toString() {
        return "Position{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
