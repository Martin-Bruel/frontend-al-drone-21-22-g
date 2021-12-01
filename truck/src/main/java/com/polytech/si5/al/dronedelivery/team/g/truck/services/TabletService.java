package com.polytech.si5.al.dronedelivery.team.g.truck.services;
import com.polytech.si5.al.dronedelivery.team.g.truck.dto.DronePositionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

@Service
public class TabletService {

    @Autowired
    private RedisOperations<String, DronePositionDto> redisTemplate;

    Logger logger = LoggerFactory.getLogger(TabletService.class);

    @Value("${redis.drone.topic}")
    private String topic;

    public void publish(DronePositionDto dronePositionDto){
        logger.info("Publish drone position : " + dronePositionDto);
        this.redisTemplate.convertAndSend(topic, dronePositionDto);
    }

}
