package com.yeshenko.actor.activemq;

import com.yeshenko.actor.service.ActorJpaService;
import com.yeshenko.common.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.yeshenko.common.Constants.METHOD_EXITED;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_LIST;
import static com.yeshenko.common.Constants.NAME_OF_FIRST_QUEUE_ACTIVE_MQ;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class ActorConsumer {
    private ActorJpaService actorJpaService;
    private ActorProducer actorProducer;

    /**
     * Listen queue with name "moviesIds" from activeMQ.
     * Find and save all directors, actors, actresses by list of movies ids.
     * Call method for send directors ids in ActiveMq queue for third app.
     *
     * @param moviesFromFirstModule list of string values
     */
    @JmsListener(destination = NAME_OF_FIRST_QUEUE_ACTIVE_MQ)
    public void getMoviesIdsFromQueue(List<String> moviesFromFirstModule) {
        log.debug(METHOD_STARTED_WORK_WITH_LIST,
                "getMoviesIdsFromQueue(List<String> moviesFromFirstModule)",
                moviesFromFirstModule.size());
        List<Person> allPeopleByMovies = actorJpaService.saveAllPeopleByMoviesId(moviesFromFirstModule);
        actorProducer.sendDirectorsIds(allPeopleByMovies);
        log.debug(METHOD_EXITED, "getMoviesIdsFromQueue(List<String> moviesFromFirstModule)");
    }
}
