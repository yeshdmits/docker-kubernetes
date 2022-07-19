package com.yeshenko.actor.activemq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.Collections;
import java.util.List;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Configuration
public class ActiveConfig {
    /**
     * Convert messages to and from JSON.
     *
     * @return MappingJackson2MessageConverter object
     */
    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
        mappingJackson2MessageConverter.setTypeIdPropertyName("content-type");
        mappingJackson2MessageConverter.setTypeIdMappings(Collections.singletonMap("list", List.class));
        return mappingJackson2MessageConverter;
    }
}
