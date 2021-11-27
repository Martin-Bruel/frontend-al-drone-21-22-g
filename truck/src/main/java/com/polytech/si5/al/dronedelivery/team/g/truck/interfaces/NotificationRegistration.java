package com.polytech.si5.al.dronedelivery.team.g.truck.interfaces;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Notification;

public interface NotificationRegistration {
    void createNotification(long packageId, int deliverySate );
    void registerNotification(Notification n);
}
