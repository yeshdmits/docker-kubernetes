package com.yeshenko.actor.service;

import com.yeshenko.actor.dto.db.Actor;
import com.yeshenko.actor.dto.db.HardworkingActor;
import com.yeshenko.actor.repository.ActorJpaRepository;
import com.yeshenko.common.entity.Movie;
import com.yeshenko.common.entity.Person;
import com.yeshenko.common.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_LIST;
import static com.yeshenko.common.entity.Role.ACTOR;
import static com.yeshenko.common.entity.Role.ACTRESS;
import static com.yeshenko.common.entity.Role.DIRECTOR;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class ActorJpaService {

    private ActorJpaRepository actorJpaRepository;
    private ActorFileService actorFileService;
    private MovieJpaService movieJpaService;

    /**
     * Get all people by "ACTOR" and "ACTRESS" role from database.
     *
     * @return list of Actor objects
     */
    @Transactional(readOnly = true)
    public List<Actor> getAll() {
        log.info(METHOD_STARTED_WORK, "getAll()");
        List<Role> rolesOfActor = new ArrayList<>(Arrays.asList(ACTOR, ACTRESS));
        List<Person> people = actorJpaRepository.findAllByRoleIn(rolesOfActor);
        List<Actor> actors = new ArrayList<>();
        people.forEach(person -> actors.add(new Actor(person.getFullName())));
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getAll()", actors.size());
        return actors;
    }

    /**
     * Get all people, which have max count of movies in database and have role "ACTOR" or "ACTRESS".
     *
     * @return list of HardworkingActor objects
     */
    @Transactional(readOnly = true)
    public List<HardworkingActor> getAllHardworkingActors() {
        log.info(METHOD_STARTED_WORK, "getAllHardworkingActors()");
        List<HardworkingActor> hardworkingActors = actorJpaRepository.findAllHardworkingActors();
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getAllHardworkingActors()", hardworkingActors.size());
        return hardworkingActors;
    }

    /**
     * Save people into database, which have movie with specific id in file system.
     *
     * @param moviesFromFirstModule list of string values
     * @return list of Person objects
     */
    @Transactional
    public List<Person> saveAllPeopleByMoviesId(List<String> moviesFromFirstModule) {
        log.info(METHOD_STARTED_WORK_WITH_LIST,
                "saveAllPeopleByMoviesId(List<String> moviesFromFirstModule",
                moviesFromFirstModule.size());
        List<Person> allPeopleByMovies = new ArrayList<>();

        moviesFromFirstModule.forEach(movieId -> {
            List<Person> people = actorFileService.getListOfPeopleByIdMovie(movieId);
            List<String> idPeople = actorJpaRepository.findAllId();
            prepareListOfPeopleToSave(people, movieId, idPeople);
            allPeopleByMovies.addAll(actorJpaRepository.saveAll(people));
        });
        log.info(METHOD_EXITED_BY_RETURNING_LIST,
                "saveAllPeopleByMoviesId(List<String> moviesFromFirstModule",
                allPeopleByMovies.size());
        return allPeopleByMovies;
    }

    /**
     * Find all directors from list of people.
     *
     * @param people list of Person objects
     * @return list of string values
     */
    public List<String> getDirectorsIdsByPeopleList(List<Person> people) {
        List<Long> idDirectors = people
                .stream()
                .filter(person -> person.getRole().equals(DIRECTOR))
                .map(Person::getId)
                .collect(Collectors.toList());

        return idDirectors.stream().map(String::valueOf).collect(Collectors.toList());
    }

    /**
     * Set for each person from list: movie by movie id and stringId for database.
     *
     * @param people list of Person objects
     * @param movieId string value
     * @param idPeople list of string values
     */
    private void prepareListOfPeopleToSave(List<Person> people, String movieId, List<String> idPeople) {
        people.forEach(personForSave -> {
            personForSave.setMovies(new HashSet<>(Collections.singletonList(movieJpaService.getById(movieId))));
            String stringId = personForSave.getStringId();
            if (idPeople.contains(stringId)) {
                List<Person> existingPeople = actorJpaRepository.findAllByStringId(stringId);
                existingPeople.forEach(existingPerson -> {
                    if (existingPerson.getRole().equals(personForSave.getRole())) {
                        Set<Movie> movies = existingPerson.getMovies();
                        movies.addAll(personForSave.getMovies());
                        personForSave.setMovies(movies);
                        personForSave.setId(existingPerson.getId());
                    }
                });
            }
        });
    }
}
