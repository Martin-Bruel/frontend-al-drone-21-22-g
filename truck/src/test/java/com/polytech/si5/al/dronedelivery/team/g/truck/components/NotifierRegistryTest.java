package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.constants.DeliveryStatusCode;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Notification;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.NotificationFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.NotificationModifier;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.NotificationRegistration;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NotifierRegistryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    NotificationRegistration notificationRegistration;

    @Autowired
    NotificationFinder notificationFinder;

    @Autowired
    NotificationModifier notificationModifier;

    private long packageId;
    private int deliverySate;

    @Before
    void setUp() {
        packageId = 6;
        deliverySate = DeliveryStatusCode.PACKAGE_DELIVERED;
    }

    @AfterEach
    public void cleanup() {
        entityManager.clear();
    }

    @Test
    @Transactional
    void getAllNotificationTest(){
        // check that there is no notification in Db
        int nbNotificationInDb = notificationFinder.getAllNotification().size();
        assertEquals(0, nbNotificationInDb);
        // create some notifications and persist them with the entityManager
        Notification notification1 = new Notification(packageId, deliverySate);
        Notification notification2 = new Notification(packageId, deliverySate);
        entityManager.persist(notification1);
        entityManager.persist(notification2);
        // check that the 2 notification are in database
        nbNotificationInDb = notificationFinder.getAllNotification().size();
        assertEquals(2, nbNotificationInDb);
        // check that the content of a notification is correct
        Notification aNotificationInDb = notificationFinder.getAllNotification().get(0);
        assertEquals(packageId, aNotificationInDb.getPackageId());
        assertEquals(deliverySate, aNotificationInDb.getDeliveryState());
    }

    @Test
    @Transactional
    void CreateNotificationTest(){
        // create a notification with the notificationRegistration
        notificationRegistration.createNotification(packageId, deliverySate);
        // check that the notification is created in database
        Notification notificationInDb = notificationFinder.getAllNotification().get(0);
        assertEquals(packageId, notificationInDb.getPackageId());
        assertEquals(deliverySate, notificationInDb.getDeliveryState());
    }

    @Test
    @Transactional
    void registerNotificationTest(){
        // create a notification and persist it with the notificationRegistration
        Notification notification = new Notification(packageId, deliverySate);
        notificationRegistration.registerNotification(notification);
        // check that the notification is created in database
        Notification notificationInDb = notificationFinder.getAllNotification().get(0);
        assertEquals(packageId, notificationInDb.getPackageId());
        assertEquals(deliverySate, notificationInDb.getDeliveryState());
    }

    @Test
    @Transactional
    void deleteNotificationsByIds(){
        // create some notifications and persist them with the entityManager
        Notification notification1 = new Notification(packageId, deliverySate);
        Notification notification2 = new Notification(packageId, deliverySate);
        entityManager.persist(notification1);
        entityManager.persist(notification2);
        // Retrieve notifications from the database and get a list of their ids
        List<Notification> notificationList = notificationFinder.getAllNotification();
        List<Long> notificationsIdsList = notificationList.stream().map(Notification::getId).collect(Collectors.toList());
        // Delete the notifications with the notificationModifier.deleteNotificationsByIds
        notificationModifier.deleteNotificationsByIds(notificationsIdsList);
        // check that the notifications have been deleted
        int nbNotificationInDb = notificationFinder.getAllNotification().size();
        assertEquals(0, nbNotificationInDb);
    }



}
