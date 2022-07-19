package com.yeshenko.director.repository.impl;

import com.yeshenko.common.entity.Movie;
import com.yeshenko.common.entity.Person;
import com.yeshenko.common.error.handler.exceptions.FileNotFoundException;
import com.yeshenko.director.repository.DirectorFileRepository;
import com.yeshenko.director.util.ColumnIdConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.yeshenko.common.Constants.DIRECTOR_LOWER_CASE;
import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_OBJECT;
import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_PARAM;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_PARAM;
import static com.yeshenko.common.Constants.MOVIES_FILE;
import static com.yeshenko.common.Constants.PEOPLE_FILE;
import static com.yeshenko.common.Constants.PROBLEM_WITH_FILE;
import static com.yeshenko.common.Constants.RATINGS_FILE;
import static com.yeshenko.common.Constants.ROLES_FILE;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Slf4j
@Repository
public class DirectorFileRepositoryImpl implements DirectorFileRepository {

    private final String moviesFile;
    private final String peopleFile;
    private final String rolesFile;
    private final String ratingsFile;

    public DirectorFileRepositoryImpl(@Value("${path.file.movies}") String moviesFile,
                                      @Value("${path.file.people}") String peopleFile,
                                      @Value("${path.file.roles}") String rolesFile,
                                      @Value("${path.file.rating}") String ratingsFile) {
        this.moviesFile = moviesFile;
        this.peopleFile = peopleFile;
        this.rolesFile = rolesFile;
        this.ratingsFile = ratingsFile;
    }

    /**
     * Get all id movies by id director.
     *
     * @param directorId string value
     * @return list of string values
     */
    @Override
    public List<String> readMoviesIdByDirector(String directorId) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "readMoviesIdByDirector(String directorId)", directorId);
        String line;
        List<String> allIdMovies = new ArrayList<>();
        Path path = Paths.get(rolesFile);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (StringUtils.isNotEmpty((line = reader.readLine()))) {
                String[] strings = line.split("\t");
                if (Objects.equals(strings[ColumnIdConstants.COLUMN_ID_DIRECTOR], directorId) &&
                        Objects.equals(strings[ColumnIdConstants.COLUMN_ROLE_DIRECTOR], DIRECTOR_LOWER_CASE)) {
                    allIdMovies.add(strings[0]);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, ROLES_FILE);
            throw new FileNotFoundException(ROLES_FILE);
        }
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "readMoviesIdByDirector(String directorId)", allIdMovies.size());
        return allIdMovies;
    }

    /**
     * Get all directors by name which give user.
     *
     * @param directorName string value
     * @return list of string values
     */
    @Override
    public List<String> getPersonIdByName(String directorName) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getPersonIdByName(String directorName)", directorName);
        String line;
        List<String> personId = new ArrayList<>();
        Path path = Paths.get(peopleFile);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (StringUtils.isNotEmpty((line = reader.readLine()))) {
                String[] strings = line.split("\t");
                if (Objects.equals(strings[ColumnIdConstants.COLUMN_FOR_FIND_ID_DIRECTOR], directorName)) {
                    personId.add(strings[0]);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, PEOPLE_FILE);
            throw new FileNotFoundException(PEOPLE_FILE);
        }
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getPersonIdByName(String directorName)", personId.size());
        return personId;
    }

    /**
     * Give list all moviesId with id and change id on name.
     *
     * @param moviesId list of string values
     * @return list of string values
     */
    @Override
    public List<String> readAllMoviesNameByMoviesId(List<String> moviesId) {
        log.info(METHOD_STARTED_WORK_WITH_LIST, "readAllMoviesNameByMoviesId(List<String> moviesId)", moviesId.size());
        String line;
        List<String> moviesName = new ArrayList<>();
        Path path = Paths.get(moviesFile);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (StringUtils.isNotEmpty((line = reader.readLine()))) {
                String[] strings = line.split("\t");
                for (String idMovies : moviesId) {
                    if (Objects.equals(strings[ColumnIdConstants.COLUMN_ID_MOVIES], idMovies) && Objects.equals(strings[ColumnIdConstants.COLUMN_CHECK_ON_MOVIE], "movie")) {
                        moviesName.add(strings[2]);
                    }
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, MOVIES_FILE);
            throw new FileNotFoundException(MOVIES_FILE);
        }
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "readAllMoviesNameByMoviesId(List<String> moviesId)", moviesName.size());
        return moviesName;
    }

    /**
     * Give list all movies with full information
     *
     * @param moviesId list of string values
     * @param people   set of Person objects
     * @return list of Movie objects
     */
    @Override
    public List<Movie> readAllMoviesForSaveToDB(List<String> moviesId, Set<Person> people) {
        log.info(METHOD_STARTED_WORK_WITH_LIST, "readAllMoviesForSaveToDB(List<String> moviesId, Set<Person> person)",
                moviesId.size());
        String line;
        List<Movie> movies = new ArrayList<>();
        Path path = Paths.get(moviesFile);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (StringUtils.isNotEmpty((line = reader.readLine()))) {
                String[] strings = line.split("\t");
                for (String idMovies : moviesId) {
                    if (Objects.equals(strings[ColumnIdConstants.COLUMN_ID_MOVIES], idMovies) &&
                            Objects.equals(strings[ColumnIdConstants.COLUMN_CHECK_ON_MOVIE], "movie")) {
                        Movie movie = new Movie();
                        movie.setId(strings[0]);
                        movie.setName(strings[2]);
                        movie.setDateRelease(checkOnDateRelease(strings[5]));
                        movie.setRating(findRatingByIdMovie(idMovies));
                        movie.setPeople(people);
                        movies.add(movie);
                    }
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, MOVIES_FILE);
            throw new FileNotFoundException(MOVIES_FILE);
        }
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "readAllMoviesForSaveToDB(List<String> moviesId, Set<Person> person)",
                movies.size());
        return movies;
    }

    /**
     * Get rating by specific movie
     * If the movie has no rating, -1 is given.
     *
     * @param idMovie string value
     * @return double value
     */
    private Double findRatingByIdMovie(String idMovie) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "findRatingByIdMovie(String idMovie)", idMovie);
        String line;
        Path path = Paths.get(ratingsFile);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (StringUtils.isNotEmpty((line = reader.readLine()))) {
                String[] strings = line.split("\t");
                if (Objects.equals(strings[0], idMovie)) {
                    log.info(METHOD_EXITED_BY_RETURNING_PARAM, "findRatingByIdMovie(String idMovie)", strings[1]);
                    return Double.valueOf(strings[1]);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, RATINGS_FILE);
            throw new FileNotFoundException(RATINGS_FILE);
        }
        log.info(METHOD_EXITED_BY_RETURNING_OBJECT, "findRatingByIdMovie(String idMovie)");
        return -1.0;
    }

    private Integer checkOnDateRelease(String year) {
        if (year.equals("\\N")) {
            return -1;
        }
        return Integer.parseInt(year);
    }
}