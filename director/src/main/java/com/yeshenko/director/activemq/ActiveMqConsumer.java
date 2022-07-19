package com.yeshenko.director.activemq;

import com.yeshenko.director.service.DirectorJpaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.yeshenko.common.Constants.NAME_OF_SECOND_QUEUE_ACTIVE_MQ;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ActiveMqConsumer {

    private final DirectorJpaService directorJpaService;

    /**
     * Listen queue with name "directorsIds" from activeMQ.
     * Find and save all movies, by list of directors ids.
     *
     * @param messages list of string values
     */
    @JmsListener(destination = NAME_OF_SECOND_QUEUE_ACTIVE_MQ)
    public void processMessages(List<String> messages) {
        List<Long> longStream = messages.stream().map(Long::valueOf).collect(Collectors.toList());
        directorJpaService.saveAllMoviesByDirector(longStream);
    }
}
