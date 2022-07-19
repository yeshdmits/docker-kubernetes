package com.yeshenko.director.repository.impl;

import com.yeshenko.common.entity.Movie;
import com.yeshenko.common.entity.Person;
import com.yeshenko.director.repository.DirectorFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DirectorFileRepositoryImplTest {

    private final String MOVIE_FILE_PATH = "../datasets-test/movies-test.tsv";
    private final String PEOPLE_FILE_PATH = "../datasets-test/people-test.tsv";
    private final String ROLE_FILE_PATH = "../datasets-test/roles-test.tsv";
    private final String RATING_FILE_PATH = "../datasets-test/ratings-test.tsv";

    @Spy
    private DirectorFileRepository directorFileRepository =
            new DirectorFileRepositoryImpl(MOVIE_FILE_PATH, PEOPLE_FILE_PATH, ROLE_FILE_PATH, RATING_FILE_PATH);

    @Test
    void shouldReadMoviesIdByDirectorId() {
        String directorId = "nm0000006";
        List<String> moviesId = directorFileRepository.readMoviesIdByDirector(directorId);
        String movieId = moviesId.stream().findFirst().orElse("null");
        assertEquals(movieId, "tt0000006");
    }

    @Test
    void shouldGetPersonIdByName() {
        String name = "Olivia de Havilland";
        List<String> personIdByName = directorFileRepository.getPersonIdByName(name);
        assertEquals(personIdByName.size(), 2);
        String directorId = personIdByName.stream().findFirst().orElse("null");
        assertEquals(directorId, "nm0000011");
    }

    @Test
    void shouldReadAllMoviesNameByMoviesId() {
        List<String> list = new ArrayList<>();
        list.add("tt0000010");
        List<String> list1 = directorFileRepository.readAllMoviesNameByMoviesId(list);
        String movieName = list1.stream().findFirst().orElse("null");
        assertEquals(movieName, "Leaving the Factory");
    }

    @Test
    void shouldReadAllMoviesByIdAndSetAllInformation() {
        Person person = new Person();
        person.setFullName("Test");

        Set<Person> people = new HashSet<>();
        people.add(person);

        List<String> moviesId = new ArrayList<>();
        moviesId.add("tt0000018");

        List<Movie> movies = directorFileRepository.readAllMoviesForSaveToDB(moviesId, people);
        Movie movie = movies.stream().findFirst().orElse(null);
        assert movie != null;
        assertEquals(movie.getName(), "Das boxende Känguruh");
        assertEquals(movie.getRating(), 5.3);
        String personName = movie
                .getPeople()
                .stream()
                .map(Person::getFullName)
                .collect(Collectors.toList())
                .stream()
                .findFirst()
                .orElse("null");
        assertEquals(personName, "Test");
    }
}