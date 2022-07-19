package com.yeshenko.movie.repository.impl;

import com.yeshenko.common.Constants;
import com.yeshenko.common.error.handler.exceptions.FileNotFoundException;
import com.yeshenko.movie.repository.MovieFileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieFileRepositoryExceptionTest {

    private final String MOVIE_FILE_PATH = "../datasets-test/mov-test.tsv";
    private final String RATING_FILE_PATH = "../datasets-test/rate-test.tsv";

    @Spy
    private MovieFileRepository repository = new MovieFileRepositoryImpl(MOVIE_FILE_PATH, RATING_FILE_PATH);

    @Test
    void readMoviesByPeriodShouldThrowFileNotFountException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            repository.readMoviesByPeriod(1968, 1978);
        });

        Assertions.assertEquals(Constants.MOVIES_FILE, thrown.getFileName());
    }

    @Test
    void readRatingsShouldThrowFileNotFountException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            repository.readRatings();
        });

        Assertions.assertEquals(Constants.RATINGS_FILE, thrown.getFileName());
    }

    @Test
    void readAllMoviesShouldThrowFileNotFountException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            repository.readAllMovies();
        });

        Assertions.assertEquals(Constants.MOVIES_FILE, thrown.getFileName());
    }

    @Test
    void readMoviesByNameShouldThrowFileNotFountException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            repository.readMoviesByName("Movie name");
        });

        Assertions.assertEquals(Constants.MOVIES_FILE, thrown.getFileName());
    }
}