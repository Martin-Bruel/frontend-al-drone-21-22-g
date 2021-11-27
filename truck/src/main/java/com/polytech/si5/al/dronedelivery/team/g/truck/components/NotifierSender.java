package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Notification;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.NotificationFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.NotificationModifier;
import com.polytech.si5.al.dronedelivery.team.g.truck.services.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotifierSender {

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    NotificationFinder notificationFinder;

    @Autowired
    NotificationModifier notificationModifier;

    private static final Logger logger = LoggerFactory.getLogger(NotifierSender.class);

    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        List<Notification> notifications = notificationFinder.getAllNotification();
        if(!notifications.isEmpty()){
            sendNotificationList(notifications);
        }
    }

    private void sendNotificationList(List<Notification> notificationList) {
        try {
            warehouseService.sendNotifications(notificationList.toArray(new Notification[0]));
            cleanNotifications(notificationList);
        } catch (ResourceAccessException e){
            logger.warn("The warehouse can't be joined, the notifications will send later");
        }
    }

    private void cleanNotifications(List<Notification> notificationList){
        logger.info("The notifications have been sent");
        List<Long> notificationsIds = notificationList.stream().map(Notification::getId).collect(Collectors.toList());
        notificationModifier.deleteNotificationsByIds(notificationsIds);
    }
}
