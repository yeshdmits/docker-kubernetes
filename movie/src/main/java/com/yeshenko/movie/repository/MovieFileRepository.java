package com.yeshenko.movie.repository;

import java.util.List;
import java.util.Map;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
public interface MovieFileRepository {

    Map<String, Double> readRatings();

    List<String[]> readMoviesByPeriod(int fromYear, int toYear);

    List<String[]> readAllMovies();

    List<String[]> readMoviesByName(String name);
}