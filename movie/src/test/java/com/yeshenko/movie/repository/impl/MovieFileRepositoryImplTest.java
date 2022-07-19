package com.yeshenko.movie.repository.impl;

import com.yeshenko.movie.repository.MovieFileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class MovieFileRepositoryImplTest {

    private final String MOVIE_FILE_PATH = "../datasets-test/movies-test.tsv";
    private final String RATING_FILE_PATH = "../datasets-test/ratings-test.tsv";

    @Spy
    private MovieFileRepository repository = new MovieFileRepositoryImpl(MOVIE_FILE_PATH, RATING_FILE_PATH);

    @Test
    void readMoviesByPeriod() {
        List<String[]> allMoviesByPeriod = repository.readMoviesByPeriod(1892, 1894);
        Assertions.assertEquals(3, allMoviesByPeriod.size());
        Assertions.assertEquals("1894", allMoviesByPeriod.get(1)[5]);
        Assertions.assertEquals("movie", allMoviesByPeriod.get(1)[1]);
        Assertions.assertEquals("Chinese Opium Den", allMoviesByPeriod.get(1)[2]);
    }

    @Test
    void readRatings() {
        Map<String, Double> ratings = repository.readRatings();
        Assertions.assertEquals(20, ratings.size());
        Assertions.assertEquals(7.1, ratings.get("tt0000014"));
    }

    @Test
    void readAllMovies() {
        List<String[]> allMovies = repository.readAllMovies();
        Assertions.assertEquals(5, allMovies.size());
        Assertions.assertEquals("movie", allMovies.get(0)[1]);
        Assertions.assertEquals("1892", allMovies.get(0)[5]);
    }

    @Test
    void readMoviesByName() {
        List<String[]> allMoviesByName = repository.readMoviesByName("Chinese Opium Den");
        Assertions.assertEquals(1, allMoviesByName.size());
        Assertions.assertEquals("movie", allMoviesByName.get(0)[1]);
        Assertions.assertEquals("1894", allMoviesByName.get(0)[5]);
    }
}