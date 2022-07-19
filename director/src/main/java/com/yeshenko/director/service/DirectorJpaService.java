package com.yeshenko.director.service;

import com.yeshenko.common.entity.Movie;
import com.yeshenko.common.entity.Person;
import com.yeshenko.common.entity.Role;
import com.yeshenko.director.dto.DirectorDto;
import com.yeshenko.director.repository.DirectorFileRepository;
import com.yeshenko.director.repository.DirectorJpaRepository;
import com.yeshenko.director.repository.MovieByDirectorJpaRepository;
import com.yeshenko.director.util.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.yeshenko.common.Constants.METHOD_EXITED;
import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_PARAM;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class DirectorJpaService {

    private DirectorJpaRepository directorJpaRepository;
    private DirectorFileRepository repository;
    private MovieByDirectorJpaRepository movieRepository;

    /**
     * Get all people by role "director" which saved in the database.
     * Database save request status
     *
     * @return list of DirectorDto objects
     */
    @Transactional(readOnly = true)
    public List<DirectorDto> findAllDirectors() {
        log.info(METHOD_STARTED_WORK, "findAllDirectors()");
        List<Person> allByRoles = directorJpaRepository.findAllByRole(Role.DIRECTOR);
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "findAllDirectors()", allByRoles.size());
        return PersonMapper.mapPersonToDirector(allByRoles);
    }

    /**
     * Get all people with role DIRECTOR by rating.
     *
     * @return list of DirectorDto objects
     */
    public List<DirectorDto> findDirectorsByRating() {
        log.info(METHOD_STARTED_WORK, "findDirectorsByRating()");
        List<DirectorDto> allDirectors = findAllDirectors();
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "findDirectorsByRating()", allDirectors.size());
        return allDirectors
                .stream()
                .sorted(Comparator.comparingInt(DirectorDto::getNumberOfFilm).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Save to database all movies one director
     * 1) find all ids movies
     * 2) get object director from database
     * 3) get full object all movies
     * 4) check for uniqueness of movies
     * 5) save all movies
     *
     * @param directorId list of long values
     */
    @Transactional
    public void saveAllMoviesByDirector(List<Long> directorId) {
        log.info(METHOD_STARTED_WORK_WITH_LIST, "saveAllMoviesByDirector(List<String> directorId)", directorId.size());
        directorId.forEach(key -> {
            List<Movie> allMovies = getAllMoviesById(key);
            log.debug("'allMovies' size = " + allMovies);
            movieRepository.saveAll(allMovies);
        });
        log.info(METHOD_EXITED, "saveAllMoviesByDirector(List<String> directorId)");
    }

    /**
     * Get all movies by id of movie.
     *
     * @param id long value
     * @return list of Movie objects
     */
    private List<Movie> getAllMoviesById(Long id) {

        log.debug(METHOD_STARTED_WORK_WITH_PARAM, "getAllMoviesById(String id)", id);
        Person personById = directorJpaRepository.findPersonById(id);
        Set<Person> personSet = new HashSet<>();
        personSet.add(personById);

        List<String> moviesById = repository.readMoviesIdByDirector(personById.getStringId());

        List<Movie> movies = repository.readAllMoviesForSaveToDB(moviesById, personSet);
        List<String> allIdsFromBD = movieRepository.findAllId();

        return movies.stream()
                .filter(movie -> !allIdsFromBD.contains(movie.getId()))
                .collect(Collectors.toList());
    }
}
