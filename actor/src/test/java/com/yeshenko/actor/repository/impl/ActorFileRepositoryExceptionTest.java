package com.yeshenko.actor.repository.impl;

import com.yeshenko.actor.repository.ActorFileRepository;
import com.yeshenko.common.Constants;
import com.yeshenko.common.error.handler.exceptions.FileNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ActorFileRepositoryExceptionTest {

    private final String MOVIE_FILE_PATH = "../datasets-test/movie-test.tsv";
    private final String PEOPLE_FILE_PATH = "../datasets-test/peop-test.tsv";
    private final String ROLE_FILE_PATH = "../datasets-test/role-test.tsv";
    private final List<String> EMPTY_LIST = new ArrayList<>();

    @Spy
    private ActorFileRepository actorFileRepository = new ActorFileRepositoryImpl(MOVIE_FILE_PATH, ROLE_FILE_PATH, PEOPLE_FILE_PATH);

    @Test
    void getIdOfMoviesByMovieTitleShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            actorFileRepository.getIdOfMoviesByMovieTitle("Movie title test");
        });

        Assertions.assertEquals(Constants.MOVIES_FILE, thrown.getFileName());
    }

    @Test
    void getIdOfActorsByMoviesIdShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            actorFileRepository.getIdOfActorsByMoviesId("test id");
        });

        Assertions.assertEquals(Constants.ROLES_FILE, thrown.getFileName());
    }

    @Test
    void getActorsByActorsIdShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            actorFileRepository.getActorsByActorsId(new ArrayList<>());
        });

        Assertions.assertEquals(Constants.PEOPLE_FILE, thrown.getFileName());
    }

    @Test
    void getIdOfPeopleByPersonFullNameShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            actorFileRepository.getIdOfPeopleByPersonFullName("full name");
        });

        Assertions.assertEquals(Constants.PEOPLE_FILE, thrown.getFileName());
    }

    @Test
    void getIdOfMoviesByActorIdShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            actorFileRepository.getIdOfMoviesByActorId("test id");
        });

        Assertions.assertEquals(Constants.ROLES_FILE, thrown.getFileName());
    }

    @Test
    void getMoviesByMoviesIdShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            actorFileRepository.getMoviesByMoviesId(new ArrayList<>());
        });

        Assertions.assertEquals(Constants.MOVIES_FILE, thrown.getFileName());
    }

    @Test
    void getListOfPeopleByIdMovieShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            actorFileRepository.getListOfPeopleByIdMovie("test id");
        });

        Assertions.assertEquals(Constants.ROLES_FILE, thrown.getFileName());
    }
}