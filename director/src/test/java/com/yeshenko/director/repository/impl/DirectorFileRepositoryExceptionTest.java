package com.yeshenko.director.repository.impl;

import com.yeshenko.common.Constants;
import com.yeshenko.common.error.handler.exceptions.FileNotFoundException;
import com.yeshenko.director.repository.DirectorFileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class DirectorFileRepositoryExceptionTest {

    private final String MOVIE_FILE_PATH = "../datasets-test/movie-test.tsv";
    private final String PEOPLE_FILE_PATH = "../datasets-test/peop-test.tsv";
    private final String ROLE_FILE_PATH = "../datasets-test/role-test.tsv";
    private final String RATING_FILE_PATH = "../datasets-test/rating-test.tsv";

    @Spy
    private DirectorFileRepository directorFileRepository =
            new DirectorFileRepositoryImpl(MOVIE_FILE_PATH, PEOPLE_FILE_PATH, ROLE_FILE_PATH, RATING_FILE_PATH);

    @Test
    void readMoviesIdByDirectorIdShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            directorFileRepository.readMoviesIdByDirector("1212");
        });

        Assertions.assertEquals(Constants.ROLES_FILE, thrown.getFileName());
    }

    @Test
    void getPersonIdByNameShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            directorFileRepository.getPersonIdByName("Director Name Test");
        });

        Assertions.assertEquals(Constants.PEOPLE_FILE, thrown.getFileName());
    }

    @Test
    void readAllMoviesNameByMoviesIdShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            directorFileRepository.readAllMoviesNameByMoviesId(new ArrayList<>());
        });

        Assertions.assertEquals(Constants.MOVIES_FILE, thrown.getFileName());
    }

    @Test
    void readAllMoviesForSaveToDbShouldThrowFileNotFoundException() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            directorFileRepository.readAllMoviesForSaveToDB(new ArrayList<>(), new HashSet<>());
        });

        Assertions.assertEquals(Constants.MOVIES_FILE, thrown.getFileName());
    }
}