package com.yeshenko.actor.controller;

import com.yeshenko.actor.dto.db.Actor;
import com.yeshenko.actor.dto.db.HardworkingActor;
import com.yeshenko.actor.dto.file.ActorsOfTheMovie;
import com.yeshenko.actor.dto.file.MoviesOfTheActor;
import com.yeshenko.actor.service.ActorFileService;
import com.yeshenko.actor.service.ActorJpaService;
import com.yeshenko.common.entity.Request;
import com.yeshenko.common.error.handler.exceptions.DataNotFoundException;
import com.yeshenko.common.error.handler.exceptions.InvalidParameterException;
import com.yeshenko.common.service.RequestJpaService;
import com.yeshenko.common.util.RequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.yeshenko.common.Constants.LIST_IS_EMPTY;
import static com.yeshenko.common.Constants.PARAM_IS_BLANK;
import static com.yeshenko.common.entity.RequestType.ACTOR;
import static com.yeshenko.common.entity.Result.SUCCESS;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@ComponentScan(basePackages = "com.yeshenko.common")
public class ActorRestController {

    private ActorJpaService actorJpaService;
    private ActorFileService actorFileService;
    private RequestJpaService requestJpaService;

    /**
     * Get all actors.
     * Search by database.
     * If list is empty - throw DataNotFoundException.
     *
     * @param request with username for actor microservice
     * @return list of Actor objects
     */
    @GetMapping
    public List<Actor> getAllActors(HttpServletRequest request) {
        Request defaultRequest = requestJpaService.create(RequestMapper.mapDefaultRequest(request, ACTOR));
        List<Actor> actors = actorJpaService.getAll();
        updateRequest(defaultRequest);
        if (CollectionUtils.isEmpty(actors)) {
            log.warn(LIST_IS_EMPTY, "actors");
            throw new DataNotFoundException();
        }
        return actors;
    }

    /**
     * Get all hardworking actors, who have the most movies.
     * Search by database.
     * If list is empty - throw DataNotFoundException.
     *
     * @param request with username for actor microservice
     * @return list of HardworkingActor objects
     */
    @GetMapping("/hardworking")
    public List<HardworkingActor> getAllHardworkingActors(HttpServletRequest request) {
        Request defaultRequest = requestJpaService.create(RequestMapper.mapDefaultRequest(request, ACTOR));
        List<HardworkingActor> hardworkingActors = actorJpaService.getAllHardworkingActors();
        updateRequest(defaultRequest);
        if (CollectionUtils.isEmpty(hardworkingActors)) {
            log.warn(LIST_IS_EMPTY, "hardworkingActors");
            throw new DataNotFoundException();
        }
        return hardworkingActors;
    }

    /**
     * Get all movies by full name actor.
     * Search by file.
     * If list is empty - throw DataNotFoundException.
     *
     * @param fullName string value
     * @param request  with username for actor microservice
     * @return list of MoviesOfTheActor objects
     */
    @GetMapping("/movies/actor")
    public List<MoviesOfTheActor> getMoviesByActor(@RequestParam("fullName") String fullName,
                                                   HttpServletRequest request) {
        Request defaultRequest = requestJpaService.create(RequestMapper.mapDefaultRequest(request, ACTOR));
        validationInputParameter(fullName);

        List<MoviesOfTheActor> moviesOfTheActorFullName = actorFileService.getAllMoviesByActorFullName(fullName);
        updateRequest(defaultRequest);
        if (CollectionUtils.isEmpty(moviesOfTheActorFullName)) {
            log.warn(LIST_IS_EMPTY, "moviesOfTheActorFullName");
            throw new DataNotFoundException();
        }
        return moviesOfTheActorFullName;
    }

    /**
     * Get all actors by title movie.
     * Search by file.
     * If list is empty - throw DataNotFoundException.
     *
     * @param title   string value
     * @param request with username for actor microservice
     * @return list of ActorsOfTheMovie objects
     */
    @GetMapping("/movie")
    public List<ActorsOfTheMovie> getActorsByMovie(@RequestParam("title") String title, HttpServletRequest request) {
        Request defaultRequest = requestJpaService.create(RequestMapper.mapDefaultRequest(request, ACTOR));
        validationInputParameter(title);

        List<ActorsOfTheMovie> actorsOfTheMovieTitle = actorFileService.getAllByMovieTitle(title);
        updateRequest(defaultRequest);
        if (CollectionUtils.isEmpty(actorsOfTheMovieTitle)) {
            log.warn(LIST_IS_EMPTY, "actorsOfTheMovieTitle");
            throw new DataNotFoundException();
        }
        return actorsOfTheMovieTitle;
    }

    /**
     * Change result on SUCCESS and request save to database.
     *
     * @param request with username for actor microservice
     */
    private void updateRequest(Request request) {
        request.setResult(SUCCESS);
        requestJpaService.create(request);
    }

    /**
     * Validate some string for emptiness.
     *
     * @param parameter string value
     */
    private void validationInputParameter(String parameter) {
        if (StringUtils.isBlank(parameter)) {
            log.warn(PARAM_IS_BLANK);
            throw new InvalidParameterException();
        }
    }
}
