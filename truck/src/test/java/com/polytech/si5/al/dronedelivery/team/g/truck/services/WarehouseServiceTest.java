package com.polytech.si5.al.dronedelivery.team.g.truck.services;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Notification;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WarehouseServiceTest {

    WarehouseService warehouseService;

    @Mock
    Notification[] notificationToSend;

    @BeforeEach
    void setUp() {
        warehouseService = new WarehouseService(new RestTemplateBuilder());
    }

    @Ignore
    @Test
    void sendNotificationsExceptionWhenNoConnection() {
        assertThrows(ResourceAccessException.class, () ->{
            warehouseService.sendNotifications(notificationToSend);
        });
    }
}
