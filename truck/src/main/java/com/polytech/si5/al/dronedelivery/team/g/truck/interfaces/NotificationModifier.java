package com.polytech.si5.al.dronedelivery.team.g.truck.interfaces;

import java.util.List;

public interface NotificationModifier {
    void deleteNotificationsByIds(List<Long> notificationsIds);
}
