package com.yeshenko.movie.activemq;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.yeshenko.common.Constants.NAME_OF_FIRST_QUEUE_ACTIVE_MQ;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class MovieProducer {

    private JmsTemplate template;

    /**
     * Get ids of movies, send ids into queue activeMQ for second microservice.
     *
     * @param ids list of string values
     */
    public void sendMoviesIds(List<String> ids) {
        log.info("Set value to {} queue ", NAME_OF_FIRST_QUEUE_ACTIVE_MQ);
        template.convertAndSend(NAME_OF_FIRST_QUEUE_ACTIVE_MQ, ids);
    }
}
