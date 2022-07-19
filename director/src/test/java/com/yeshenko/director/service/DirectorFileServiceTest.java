package com.yeshenko.director.service;

import com.yeshenko.director.dto.DirectorDto;
import com.yeshenko.director.repository.DirectorFileRepository;
import com.yeshenko.director.repository.impl.DirectorFileRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DirectorFileServiceTest {

    private final String MOVIE_FILE_PATH = "../datasets-test/movies-test.tsv";
    private final String PEOPLE_FILE_PATH = "../datasets-test/people-test.tsv";
    private final String ROLE_FILE_PATH = "../datasets-test/roles-test.tsv";
    private final String RATING_FILE_PATH = "../datasets-test/ratings-test.tsv";

    @Spy
    private DirectorFileRepository directorFileRepository =
            new DirectorFileRepositoryImpl(MOVIE_FILE_PATH, PEOPLE_FILE_PATH, ROLE_FILE_PATH, RATING_FILE_PATH);

    @InjectMocks
    private DirectorFileService service;

    @Test
    void shouldFindMoviesByNameDirector() {
        String name = "James Cagney";
        List<DirectorDto> moviesByDirector = service.findMoviesByDirector(name);

        DirectorDto firstDto = moviesByDirector.stream().findFirst().orElse(new DirectorDto());
        assertEquals(firstDto.getNumberOfFilm(), 1);

        String movieName = firstDto.getMovies().stream().findFirst().orElse("null");
        assertEquals(movieName, "Leaving the Factory");
    }
}