package com.yeshenko.actor.service;

import com.yeshenko.actor.dto.file.ActorsOfTheMovie;
import com.yeshenko.actor.dto.file.MoviesOfTheActor;
import com.yeshenko.actor.repository.ActorFileRepository;
import com.yeshenko.common.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_PARAM;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class ActorFileService {

    private ActorFileRepository actorFileRepository;

    /**
     * Get all movies by full name of people, which also have role "actress" or "actor".
     *
     * @param actorFullName string value
     * @return list of MoviesOfTheActor objects
     */
    public List<MoviesOfTheActor> getAllMoviesByActorFullName(String actorFullName) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getAllMoviesByActorFullName(String actorFullName)", actorFullName);
        List<MoviesOfTheActor> moviesOfTheActor = new ArrayList<>();

        List<String> idPeople = actorFileRepository.getIdOfPeopleByPersonFullName(actorFullName);
        idPeople.forEach(id -> {
            List<String> idMovies = actorFileRepository.getIdOfMoviesByActorId(id);
            if (CollectionUtils.isNotEmpty(idMovies)) {
                List<String> movies = actorFileRepository.getMoviesByMoviesId(idMovies);
                moviesOfTheActor.add(new MoviesOfTheActor(actorFullName, movies));
            }
        });

        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getAllMoviesByActorFullName(String actorFullName)",
                moviesOfTheActor.size());
        return moviesOfTheActor;
    }

    /**
     * Get all people, which have role "actor" or "actress" and were in movie by movie title.
     *
     * @param movieTitle string value
     * @return list of ActorsOfTheMovie objects
     */
    public List<ActorsOfTheMovie> getAllByMovieTitle(String movieTitle) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getAllByMovieTitle(String movieTitle)", movieTitle);
        List<ActorsOfTheMovie> actorsOfTheMovie = new ArrayList<>();
        List<String> idMovies = actorFileRepository.getIdOfMoviesByMovieTitle(movieTitle);

        idMovies.forEach(id -> {
            List<String> idPeople = actorFileRepository.getIdOfActorsByMoviesId(id);
            if (CollectionUtils.isNotEmpty(idPeople)) {
                List<String> actors = actorFileRepository.getActorsByActorsId(idPeople);
                actorsOfTheMovie.add(new ActorsOfTheMovie(movieTitle, actors));
            }
        });

        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getAllByMovieTitle(String movieTitle)", actorsOfTheMovie.size());
        return actorsOfTheMovie;
    }

    /**
     * Get list of people by movie id.
     *
     * @param idMovie string value
     * @return list of Person objects
     */
    public List<Person> getListOfPeopleByIdMovie(String idMovie) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getListOfPeopleByIdMovie(String idMovie)", idMovie);
        List<Person> result = actorFileRepository.getListOfPeopleByIdMovie(idMovie);
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getListOfPeopleByIdMovie(String idMovie)", result.size());
        return result;
    }
}
