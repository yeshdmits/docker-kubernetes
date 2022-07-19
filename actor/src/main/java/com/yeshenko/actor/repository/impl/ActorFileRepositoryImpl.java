package com.yeshenko.actor.repository.impl;

import com.yeshenko.actor.repository.ActorFileRepository;
import com.yeshenko.common.Constants;
import com.yeshenko.common.entity.Person;
import com.yeshenko.common.entity.Role;
import com.yeshenko.common.error.handler.exceptions.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.yeshenko.common.Constants.ACTOR_LOWER_CASE;
import static com.yeshenko.common.Constants.ACTRESS_LOWER_CASE;
import static com.yeshenko.common.Constants.METHOD_EXITED;
import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_PARAM;
import static com.yeshenko.common.Constants.MOVIES_FILE;
import static com.yeshenko.common.Constants.PEOPLE_FILE;
import static com.yeshenko.common.Constants.PROBLEM_WITH_FILE;
import static com.yeshenko.common.Constants.ROLES_FILE;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Slf4j
@Repository
public class ActorFileRepositoryImpl implements ActorFileRepository {

    private final String moviesFilePath;
    private final String rolesFilePath;
    private final String peopleFilePath;

    public ActorFileRepositoryImpl(@Value("${path.file.movies}") String moviesFilePath,
                                   @Value("${path.file.roles}") String rolesFilePath,
                                   @Value("${path.file.people}") String peopleFilePath) {
        this.moviesFilePath = moviesFilePath;
        this.rolesFilePath = rolesFilePath;
        this.peopleFilePath = peopleFilePath;
    }

    /**
     * Get all ids of movies by title movie.
     * If it does not work correctly, it will throw FileNotFoundException.
     *
     * @param movieTitle string value
     * @return list of string values
     */
    @Override
    public List<String> getIdOfMoviesByMovieTitle(String movieTitle) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getIdOfMoviesByMovieTitle(String movieTitle)", movieTitle);
        List<String> idMovies = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(moviesFilePath))) {
            String line;
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                if (Objects.equals(strings[2], movieTitle) && Objects.equals(strings[1], Constants.MOVIE_LOWER_CASE)) {
                    idMovies.add(strings[0]);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, MOVIES_FILE);
            throw new FileNotFoundException(MOVIES_FILE);
        }

        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getIdOfMoviesByMovieTitle(String movieTitle)", idMovies.size());
        return idMovies;
    }

    /**
     * Get all id of actors by id movie.
     * If it does not work correctly, it will throw FileNotFoundException.
     *
     * @param idMovie string value
     * @return list of string values
     */
    @Override
    public List<String> getIdOfActorsByMoviesId(String idMovie) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getIdOfActorsByMoviesId(String idMovie)", idMovie);
        List<String> idActors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(rolesFilePath))) {
            String line;
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                if (Objects.equals(strings[0], idMovie) && (Objects.equals(strings[3], ACTOR_LOWER_CASE)
                        || Objects.equals(strings[3], ACTRESS_LOWER_CASE))) {
                    idActors.add(strings[2]);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, ROLES_FILE);
            throw new FileNotFoundException(ROLES_FILE);
        }

        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getIdOfActorsByMoviesId(String idMovie)", idActors.size());
        return idActors;
    }

    /**
     * Get all actors by list id.
     * If it does not work correctly, it will throw FileNotFoundException.
     *
     * @param idActors list of string values
     * @return name list string values
     */
    @Override
    public List<String> getActorsByActorsId(List<String> idActors) {
        log.info(METHOD_STARTED_WORK_WITH_LIST, "getActorsByActorsId(List<String> idActors)", idActors.size());
        List<String> actors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(peopleFilePath))) {
            String line;
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                if (idActors.contains(strings[0])) {
                    actors.add(strings[1]);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, PEOPLE_FILE);
            throw new FileNotFoundException(PEOPLE_FILE);
        }

        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getActorsByActorsId(List<String> idActors)", actors.size());
        return actors;
    }

    /**
     * Get all id of people by full name of person.
     * If it does not work correctly, it will throw FileNotFoundException
     *
     * @param personFullName string value
     * @return list of string values
     */
    @Override
    public List<String> getIdOfPeopleByPersonFullName(String personFullName) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getIdOfPeopleByPersonFullName(String personFullName)", personFullName);
        List<String> idPeople = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(peopleFilePath))) {
            String line;
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                if (Objects.equals(strings[1], personFullName)) {
                    idPeople.add(strings[0]);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, PEOPLE_FILE);
            throw new FileNotFoundException(PEOPLE_FILE);
        }

        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getIdOfPeopleByPersonFullName(String personFullName)", idPeople.size());
        return idPeople;
    }

    /**
     * Get all id of movies by id of person.
     * If it does not work correctly, it will throw FileNotFoundException
     *
     * @param idPerson string value
     * @return list sting values
     */
    @Override
    public List<String> getIdOfMoviesByActorId(String idPerson) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getIdOfMoviesByActorId(String idPerson)", idPerson);
        List<String> idMovies = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(rolesFilePath))) {
            String line;
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                if (Objects.equals(idPerson, strings[2]) && (Objects.equals(strings[3], ACTOR_LOWER_CASE)
                        || Objects.equals(strings[3], ACTRESS_LOWER_CASE))) {
                    idMovies.add(strings[0]);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, ROLES_FILE);
            throw new FileNotFoundException(ROLES_FILE);
        }

        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getIdOfMoviesByActorId(String idPerson)", idMovies.size());
        return idMovies;
    }

    /**
     * Get all movies by id of movies.
     * If it does not work correctly, it will throw FileNotFoundException.
     *
     * @param idMovies list of string values
     * @return list of string values
     */
    @Override
    public List<String> getMoviesByMoviesId(List<String> idMovies) {
        log.info(METHOD_STARTED_WORK_WITH_LIST, "getMoviesByMoviesId(List<String> idMovies)", idMovies.size());
        List<String> movies = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(moviesFilePath))) {
            String line;
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                if (idMovies.contains(strings[0]) && Objects.equals(strings[1], Constants.MOVIE_LOWER_CASE)) {
                    movies.add(strings[2]);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, MOVIES_FILE);
            throw new FileNotFoundException(MOVIES_FILE);
        }

        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getMoviesByMoviesId(List<String> idMovies)", movies.size());
        return movies;
    }

    /**
     * Get list of directors, actors and actresses ids by movie id.
     *
     * @param idMovie string value
     * @return list of Person objects
     */
    @Override
    public List<Person> getListOfPeopleByIdMovie(String idMovie) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getListOfPeopleByIdMovie(String idMovie)", idMovie);
        List<Person> peopleWithRole = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(rolesFilePath))) {
            String line;
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                String roleName = strings[3].toUpperCase();
                if (Objects.equals(strings[0], idMovie) && EnumUtils.isValidEnum(Role.class, roleName)) {
                    Person person = new Person();
                    person.setStringId(strings[2]);
                    person.setRole(Role.valueOf(roleName));
                    peopleWithRole.add(person);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, ROLES_FILE);
            throw new FileNotFoundException(ROLES_FILE);
        }
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getListOfPeopleByIdMovie(String idMovie)", peopleWithRole.size());
        setFullNameInListOfPeopleWithRole(peopleWithRole);
        return peopleWithRole;
    }

    /**
     * Get full name of people from file by list of ids.
     *
     * @param peopleWithRole list of Person objects
     */
    private void setFullNameInListOfPeopleWithRole(List<Person> peopleWithRole) {
        log.info(METHOD_STARTED_WORK_WITH_LIST, "setFullNameInListOfPeopleWithRole(List<Person> peopleWithRole)",
                peopleWithRole.size());
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(peopleFilePath))) {
            String line;
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                peopleWithRole.forEach(person -> {
                    if (person.getStringId().equals(strings[0])) {
                        person.setFullName(strings[1]);
                    }
                });
            }
            peopleWithRole.removeIf(person -> StringUtils.isEmpty(person.getFullName()));
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, PEOPLE_FILE);
            throw new FileNotFoundException(PEOPLE_FILE);
        }
        log.info(METHOD_EXITED, "setFullNameInListOfPeopleWithRole(List<Person> peopleWithRole)");
    }
}
