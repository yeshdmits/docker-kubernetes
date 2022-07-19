package com.yeshenko.movie.repository.impl;

import com.yeshenko.common.error.handler.exceptions.FileNotFoundException;
import com.yeshenko.movie.repository.MovieFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_MAP;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_PARAM;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_TWO_PARAMS;
import static com.yeshenko.common.Constants.MOVIES_FILE;
import static com.yeshenko.common.Constants.MOVIE_LOWER_CASE;
import static com.yeshenko.common.Constants.PROBLEM_WITH_FILE;
import static com.yeshenko.common.Constants.RATINGS_FILE;

/**
 * @author Yeshenko Dmitriy
 * @version 1.1
 */
@Repository
@ComponentScan(basePackages = "com.yeshenko")
@Slf4j
public class MovieFileRepositoryImpl implements MovieFileRepository {

    private final String movieFilePath;
    private final String ratingFilePath;

    public MovieFileRepositoryImpl(@Value("${file.path.movie}") String movieFilePath, @Value("${file.path.rating}") String ratingFilePath) {
        this.movieFilePath = movieFilePath;
        this.ratingFilePath = ratingFilePath;
    }

    /**
     * Read movies by period from file movies.tsv.
     *
     * @param fromYear integer value
     * @param toYear integer value
     * @return list of string arrays
     */
    @Override
    public List<String[]> readMoviesByPeriod(int fromYear, int toYear) {
        log.info(METHOD_STARTED_WORK_WITH_TWO_PARAMS, "readMoviesByPeriod(int fromYear, int toYear)", fromYear, toYear);
        List<String[]> moviesByPeriod = new ArrayList<>();

        Path path = Paths.get(movieFilePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            reader.readLine();
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                if (strings[5].equals("\\N")) {
                    continue;
                }
                int dataRelease = Integer.parseInt(strings[5]);

                if (dataRelease >= fromYear && dataRelease <= toYear && strings[1].equals(MOVIE_LOWER_CASE)) {
                    moviesByPeriod.add(strings);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, MOVIES_FILE);
            throw new FileNotFoundException(MOVIES_FILE);
        }
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "readMoviesByPeriod(int fromYear, int toYear)",
                moviesByPeriod.size());
        return moviesByPeriod;
    }

    /**
     * Reads ratings for movies from file ratings.tsv.
     *
     * @return map of string values to double values
     */
    @Override
    public Map<String, Double> readRatings() {
        log.info(METHOD_STARTED_WORK, "readRatings()");

        Map<String, Double> moviesRatings = new HashMap<>();

        Path path = Paths.get(ratingFilePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            reader.readLine();
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                String ratingStr = strings[1];

                if (ratingStr.isEmpty()) {
                    moviesRatings.put(strings[0], -1.0);
                } else {
                    moviesRatings.put(strings[0], Double.parseDouble(strings[1]));
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, RATINGS_FILE);
            throw new FileNotFoundException(RATINGS_FILE);
        }
        log.info(METHOD_EXITED_BY_RETURNING_MAP, "readRatings()", moviesRatings.size());
        return moviesRatings;
    }

    /**
     * Reads all movies from file movies.tsv.
     *
     * @return list of string arrays
     */
    @Override
    public List<String[]> readAllMovies() {
        log.info(METHOD_STARTED_WORK, "readAllMovies()");

        List<String[]> movies = new ArrayList<>();

        Path path = Paths.get(movieFilePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.readLine();
            String line;
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                if (strings[1].equals(MOVIE_LOWER_CASE)) {
                    movies.add(strings);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, MOVIES_FILE);
            throw new FileNotFoundException(MOVIES_FILE);
        }
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "readAllMovies()", movies.size());
        return movies;
    }

    /**
     * Reads all movies by title of movie.
     *
     * @param name string value
     * @return list of string arrays
     */
    @Override
    public List<String[]> readMoviesByName(String name) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "readMoviesByName(String name)", name);

        List<String[]> movies = new ArrayList<>();

        Path path = Paths.get(movieFilePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.readLine();
            String line;
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] strings = line.split("\t");
                if (strings[1].equals(MOVIE_LOWER_CASE) && strings[2].equals(name)) {
                    movies.add(strings);
                }
            }
        } catch (IOException e) {
            log.warn(PROBLEM_WITH_FILE, MOVIES_FILE);
            throw new FileNotFoundException(MOVIES_FILE);
        }
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "readMoviesByName(String name)", movies.size());
        return movies;
    }
}