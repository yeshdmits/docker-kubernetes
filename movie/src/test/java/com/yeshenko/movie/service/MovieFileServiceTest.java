package com.yeshenko.movie.service;

import com.yeshenko.movie.dto.MovieDto;
import com.yeshenko.movie.repository.MovieFileRepository;
import com.yeshenko.movie.repository.impl.MovieFileRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class MovieFileServiceTest {

    private final String MOVIE_FILE_PATH = "../datasets-test/movies-test.tsv";
    private final String RATING_FILE_PATH = "../datasets-test/ratings-test.tsv";

    @Spy
    private MovieFileRepository repository = new MovieFileRepositoryImpl(MOVIE_FILE_PATH, RATING_FILE_PATH);

    @InjectMocks
    private MovieFileService service;

    @Test
    void findTheBestMovies() {
        List<MovieDto> bestMovies = service.findTheBestMovies(3);
        Assertions.assertEquals(3, bestMovies.size());
        Assertions.assertEquals(6.9, bestMovies.get(0).getRating());
    }

    @Test
    void findMoviesByPeriod() {
        List<MovieDto> moviesByPeriod = service.findMoviesByPeriod(1894, 1895);
        Assertions.assertEquals(4, moviesByPeriod.size());
        Assertions.assertEquals(6.2, moviesByPeriod.get(2).getRating());
    }

    @Test
    void findMoviesByName() {
        String movieName = "Leaving the Factory";
        List<MovieDto> moviesByName = service.findMoviesByName(movieName);
        Assertions.assertEquals(2, moviesByName.size());
        Assertions.assertEquals(movieName, moviesByName.get(0).getName());
        Assertions.assertEquals(moviesByName.get(0).getName(), moviesByName.get(1).getName());
        Assertions.assertEquals(6.9, moviesByName.get(0).getRating());
    }
}