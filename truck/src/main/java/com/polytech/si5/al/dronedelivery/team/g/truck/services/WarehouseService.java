package com.polytech.si5.al.dronedelivery.team.g.truck.services;

import com.polytech.si5.al.dronedelivery.team.g.truck.constants.Api;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class WarehouseService {

    private final RestTemplate restTemplate;
    Logger logger = LoggerFactory.getLogger(WarehouseService.class);

    public WarehouseService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendNotifications(Notification[] notifications) throws ResourceAccessException {
        // Build URL //
        String host= Api.WAREHOUSE_API_HOST;
        String port= Api.WAREHOUSE_API_PORT;
        String url = "http://"+host+":"+port+"/"+ Api.WAREHOUSE_API_BASE_URL+"/notifications";
        // Build request //
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Notification[]> request = new HttpEntity<Notification[]>(notifications, headers);
        String response= restTemplate.postForObject(url, request, String.class);
        logger.info(response);
    }
}
