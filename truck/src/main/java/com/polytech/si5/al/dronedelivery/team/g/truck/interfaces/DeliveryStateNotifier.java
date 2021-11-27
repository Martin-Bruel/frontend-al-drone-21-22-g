package com.polytech.si5.al.dronedelivery.team.g.truck.interfaces;

public interface DeliveryStateNotifier {
    void updateDeliverySate(long droneId,int status, long deliveryId);
}
