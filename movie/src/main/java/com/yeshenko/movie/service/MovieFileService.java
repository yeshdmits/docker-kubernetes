package com.yeshenko.movie.service;

import com.yeshenko.movie.dto.MovieDto;
import com.yeshenko.movie.repository.MovieFileRepository;
import com.yeshenko.movie.util.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_PARAM;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_TWO_PARAMS;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MovieFileService {

    private final MovieFileRepository repository;

    /**
     * Get all movies and ratings, converts it to MovieDto and sorts by rating.
     * Saves successful request to database if everything worked correctly.
     *
     * @param total integer value
     * @return list of MovieDto objects
     */
    public List<MovieDto> findTheBestMovies(int total) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "findTheBestMovies(int total)", total);
        List<String[]> moviesString = repository.readAllMovies();
        List<MovieDto> movies = getMappedList(moviesString);

        List<MovieDto> bestMovies = movies
                .stream()
                .sorted(Comparator.comparingDouble(MovieDto::getRating).reversed())
                .limit(total)
                .collect(Collectors.toList());

        log.info(METHOD_EXITED_BY_RETURNING_LIST, "findTheBestMovies(int total)", bestMovies.size());
        return bestMovies;
    }

    /**
     * Get all movies by period and ratings, converts it to MovieDto.
     * Saves successful request to database if everything worked correctly
     *
     * @param fromYear integer value
     * @param toYear   integer value
     * @return list of MovieDto objects
     */
    public List<MovieDto> findMoviesByPeriod(int fromYear, int toYear) {
        log.info(METHOD_STARTED_WORK_WITH_TWO_PARAMS, "findMoviesByPeriod(int fromYear, int toYear)", fromYear, toYear);
        List<String[]> moviesByPeriod = repository.readMoviesByPeriod(fromYear, toYear);
        List<MovieDto> movies = getMappedList(moviesByPeriod);
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "findMoviesByPeriod(int fromYear, int toYear)", movies.size());
        return movies;
    }

    /**
     * Get movies by movie title.
     *
     * @param name string value
     * @return list of MovieDto objects
     */
    public List<MovieDto> findMoviesByName(String name) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "findMoviesByName(String name)", name);
        List<String[]> moviesByName = repository.readMoviesByName(name);
        List<MovieDto> movies = getMappedList(moviesByName);
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "findMoviesByName(String name)", movies.size());
        return movies;
    }

    /**
     * Maps strings to MovieDto
     * Gets all ratings from file and sets to MoVieDto
     *
     * @param movieStrings list of arrays of strings
     * @return list of MovieDto objects
     */
    private List<MovieDto> getMappedList(List<String[]> movieStrings) {
        log.info(METHOD_STARTED_WORK_WITH_LIST, "getMappedList(List<String[]> movieStrings)", movieStrings.size());
        List<MovieDto> movies = new ArrayList<>();
        Map<String, Double> ratings = repository.readRatings();

        for (int i = 0; i < movieStrings.size(); i++) {
            String movieId = movieStrings.get(i)[0];
            movies.add(MovieMapper.mapStringsToMovieDto(movieStrings.get(i), ratings.get(movieId)));
        }
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "getMappedList(List<String[]> movieStrings)", movies.size());
        return movies;
    }
}