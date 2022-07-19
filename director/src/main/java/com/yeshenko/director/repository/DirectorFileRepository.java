package com.yeshenko.director.repository;

import com.yeshenko.common.entity.Movie;
import com.yeshenko.common.entity.Person;

import java.util.List;
import java.util.Set;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
public interface DirectorFileRepository {

    List<String> readMoviesIdByDirector(String directorId);

    List<String> getPersonIdByName(String directorName);

    List<String> readAllMoviesNameByMoviesId(List<String> moviesId);

    List<Movie> readAllMoviesForSaveToDB(List<String> moviesId, Set<Person> person);
}
