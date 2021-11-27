package com.polytech.si5.al.dronedelivery.team.g.truck.dto;

import lombok.Getter;

@Getter
public class PositionDto {
    private String latitude;
    private String longitude;

    @Override
    public String toString() {
        return "Position{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
