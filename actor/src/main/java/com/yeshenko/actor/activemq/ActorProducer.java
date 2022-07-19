package com.yeshenko.actor.activemq;

import com.yeshenko.actor.service.ActorJpaService;
import com.yeshenko.common.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.yeshenko.common.Constants.METHOD_EXITED;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_LIST;
import static com.yeshenko.common.Constants.NAME_OF_SECOND_QUEUE_ACTIVE_MQ;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class ActorProducer {
    private JmsTemplate jmsTemplate;
    private ActorJpaService actorJpaService;

    /**
     * Get ids of people with role "DIRECTOR" from list of people,
     * send ids into queue activeMQ for third microservice.
     *
     * @param ids list of Person objects
     */
    public void sendDirectorsIds(List<Person> ids) {
        log.debug(METHOD_STARTED_WORK_WITH_LIST, "sendDirectorsIds(List<Person> ids)", ids.size());
        jmsTemplate.convertAndSend(NAME_OF_SECOND_QUEUE_ACTIVE_MQ, actorJpaService.getDirectorsIdsByPeopleList(ids));
        log.debug(METHOD_EXITED, "sendDirectorsIds(List<Person> ids)");
    }
}
