package com.polytech.si5.al.dronedelivery.team.g.truck.utils;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Position;

public class PositionCalculator {

    public static double distance(Position p1, Position p2){
        double xP1 = p1.getLongitude();
        double yP1 = p1.getLatitude();
        double xP2 = p2.getLongitude();
        double yP2 = p2.getLatitude();

        return Math.pow(Math.pow(Math.abs(xP1 - xP2), 2) + Math.pow(Math.abs(yP1 - yP2), 2), 0.5);
    }
}
