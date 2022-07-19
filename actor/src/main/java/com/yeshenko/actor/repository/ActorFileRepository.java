package com.yeshenko.actor.repository;

import com.yeshenko.common.entity.Person;

import java.util.List;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
public interface ActorFileRepository {

    List<String> getIdOfMoviesByMovieTitle(String movieTitle);

    List<String> getIdOfActorsByMoviesId(String idMovie);

    List<String> getActorsByActorsId(List<String> idActors);

    List<String> getIdOfPeopleByPersonFullName(String personFullName);

    List<String> getIdOfMoviesByActorId(String idPerson);

    List<String> getMoviesByMoviesId(List<String> idMovies);

    List<Person> getListOfPeopleByIdMovie(String idMovie);
}
