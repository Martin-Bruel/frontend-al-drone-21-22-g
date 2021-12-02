package com.polytech.si5.al.dronedelivery.team.g.truck.configuration;

import com.polytech.si5.al.dronedelivery.team.g.truck.dto.DronePositionDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
@Configuration
public class RedisProducerConfig {
    @Bean
    RedisTemplate<String, DronePositionDto> redisTemplate(RedisConnectionFactory connectionFactory,
                                                          Jackson2JsonRedisSerializer<DronePositionDto> serializer) {
        RedisTemplate<String, DronePositionDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    @Bean
    public Jackson2JsonRedisSerializer<DronePositionDto> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(DronePositionDto.class);
    }
}
