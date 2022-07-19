package com.yeshenko.movie.service;

import com.yeshenko.common.entity.Movie;
import com.yeshenko.movie.dto.MovieDto;
import com.yeshenko.movie.repository.MovieJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.yeshenko.common.Constants.METHOD_EXITED;
import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_PARAM;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MovieJpaService {

    private final MovieJpaRepository repository;

    /**
     * Gets all saved movies from database.
     *
     * @return list of MovieDto objects
     */
    @Transactional(readOnly = true)
    public List<MovieDto> getAllMovies() {
        log.info(METHOD_STARTED_WORK, "getAllMovies()");
        List<Movie> movies = repository.findAll();
        List<MovieDto> responseList = convertMovieToDto(movies);
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getAllMovies()", responseList.size());
        return responseList;
    }

    /**
     * Gets movies from database by name.
     *
     * @param name string value
     * @return list of MovieDto objects
     */
    @Transactional(readOnly = true)
    public List<MovieDto> getMoviesByName(String name) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getMoviesByName(String name)", name);
        List<Movie> moviesByName = repository.findByName(name);
        List<MovieDto> responseList = convertMovieToDto(moviesByName);
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getMoviesByName(String name)", responseList.size());
        return responseList;
    }

    /**
     * Create into database new movies from list of dto.
     *
     * @param movies list of MovieDto objects
     */
    @Transactional
    public void saveRequestedMovies(List<MovieDto> movies) {
        log.info(METHOD_STARTED_WORK_WITH_LIST, "saveRequestedMovies(List<MovieDto> movies)", movies.size());
        movies.forEach(movieDto -> {
            Movie movie = new Movie();
            movie.setId(movieDto.getId());
            movie.setName(movieDto.getName());
            movie.setRating(movieDto.getRating());
            movie.setDateRelease(movieDto.getDataRelease());
            repository.save(movie);
        });
        log.info(METHOD_EXITED, "saveRequestedMovies(List<MovieDto> movies)");
    }

    /**
     * Converts list of movies to list of MovieDto
     *
     * @param movies list of Movie objects
     * @return list of MovieDto objects
     */
    private List<MovieDto> convertMovieToDto(List<Movie> movies) {
        List<MovieDto> responseList = new ArrayList<>();

        movies.forEach(movie -> {
            MovieDto movieDto = new MovieDto();
            movieDto.setDataRelease(movie.getDateRelease());
            movieDto.setName(movie.getName());
            movieDto.setRating(movie.getRating());
            responseList.add(movieDto);
        });

        return responseList;
    }
}