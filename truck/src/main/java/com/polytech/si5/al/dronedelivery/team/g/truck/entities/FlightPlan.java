package com.polytech.si5.al.dronedelivery.team.g.truck.entities;

import java.util.List;

public class FlightPlan {

    private List<Step> steps;
    private Position start;

    public FlightPlan(List<Step> steps, Position start) {
        this.steps = steps;
        this.start = start;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Position getStart() {
        return start;
    }

    @Override
    public String toString() {
        return "FlightPlan{" +
                "steps=" + steps.toString() +
                ", start=" + start.toString() +
                '}';
    }
}
