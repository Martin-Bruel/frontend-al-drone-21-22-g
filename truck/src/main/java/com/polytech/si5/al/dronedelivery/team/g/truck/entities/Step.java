package com.polytech.si5.al.dronedelivery.team.g.truck.entities;

public class Step {

    private final double latitude;
    private final double longitude;
    private final Long deliveryId;

    public Step(Position position, Long deliveryId) {
        this.latitude = position.getLatitude();
        this.longitude = position.getLongitude();
        this.deliveryId = deliveryId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Step{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", deliveryId=" + deliveryId +
                '}';
    }
}
