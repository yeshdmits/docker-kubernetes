package com.yeshenko.actor.repository.impl;

import com.yeshenko.actor.repository.ActorFileRepository;
import com.yeshenko.common.entity.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.yeshenko.common.entity.Role.DIRECTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ActorFileRepositoryImplTest {

    private final String MOVIE_FILE_PATH = "../datasets-test/movies-test.tsv";
    private final String PEOPLE_FILE_PATH = "../datasets-test/people-test.tsv";
    private final String ROLE_FILE_PATH = "../datasets-test/roles-test.tsv";
    private final List<String> EMPTY_LIST = new ArrayList<>();

    @Spy
    private ActorFileRepository actorFileRepository = new ActorFileRepositoryImpl(MOVIE_FILE_PATH, ROLE_FILE_PATH, PEOPLE_FILE_PATH);

    @Test
    void shouldReturnListOfIdMoviesByMovieTitle() {
        List<String> actualListOfIdMovies = actorFileRepository.getIdOfMoviesByMovieTitle("Das boxende Känguruh");
        assertEquals(Collections.singletonList("tt0000018"), actualListOfIdMovies);
    }

    @Test
    void shouldReturnEmptyListOfIdMoviesByMovieTitle() {
        List<String> actualListOfIdMovies = actorFileRepository.getIdOfMoviesByMovieTitle("NotFound");
        assertEquals(EMPTY_LIST, actualListOfIdMovies);
    }

    @Test
    void shouldReturnListOfIdActorsByIdMovie() {
        List<String> actualListOfIdActors = actorFileRepository.getIdOfActorsByMoviesId("tt0000013");
        assertEquals(Collections.singletonList("nm0000013"), actualListOfIdActors);
    }

    @Test
    void shouldReturnEmptyListOfIdActorsByIdMovie() {
        List<String> actualListOfIdActors = actorFileRepository.getIdOfActorsByMoviesId("Tt0000013");
        assertEquals(EMPTY_LIST, actualListOfIdActors);
    }

    @Test
    void shouldReturnListOfFullNameActorsByIdActors() {
        List<String> actualListOfFullNameActors = actorFileRepository.getActorsByActorsId(Collections.singletonList("nm0000005"));
        assertEquals(Collections.singletonList("Ingmar Bergman"), actualListOfFullNameActors);
    }

    @Test
    void shouldReturnEmptyListOfFullNameActorsByIdActors() {
        List<String> actualListOfFullNameActors = actorFileRepository.getActorsByActorsId(Collections.singletonList("Nm0000005"));
        assertEquals(EMPTY_LIST, actualListOfFullNameActors);
    }

    @Test
    void shouldReturnListOfIdPeopleByFullNamePerson() {
        List<String> actualListOfIdPeople = actorFileRepository.getIdOfPeopleByPersonFullName("Ingrid Bergman");
        assertEquals(Collections.singletonList("nm0000006"), actualListOfIdPeople);
    }

    @Test
    void shouldReturnEmptyListOfIdPeopleByFullNamePerson() {
        List<String> actualListOfIdPeople = actorFileRepository.getIdOfPeopleByPersonFullName("Person Personovich");
        assertEquals(EMPTY_LIST, actualListOfIdPeople);
    }

    @Test
    void shouldReturnListOfIdMoviesByIdActor() {
        List<String> actualListOfIdMovies = actorFileRepository.getIdOfMoviesByActorId("nm0000012");
        assertEquals(Collections.singletonList("tt0000012"), actualListOfIdMovies);
    }

    @Test
    void shouldReturnEmptyListOfIdMoviesByIdActor() {
        List<String> actualListOfIdMovies = actorFileRepository.getIdOfMoviesByActorId("nm0000014");
        assertEquals(EMPTY_LIST, actualListOfIdMovies);
    }

    @Test
    void shouldReturnListOfTitleMoviesByIdMovie() {
        List<String> actualListOfTitle = actorFileRepository.getMoviesByMoviesId(Collections.singletonList("tt0000003"));
        assertEquals(Collections.singletonList("Pauvre Pierrot"), actualListOfTitle);
    }

    @Test
    void shouldReturnEmptyListOfTitleMoviesByIdMovie() {
        List<String> actualListOfTitle = actorFileRepository.getMoviesByMoviesId(Collections.singletonList("tt0000025"));
        assertEquals(EMPTY_LIST, actualListOfTitle);
    }

    @Test
    void shouldReturnListOfPersonDtoByIdMovie() {
        Person expectedPersonDto = new Person();
        expectedPersonDto.setStringId("nm0000010");
        expectedPersonDto.setRole(DIRECTOR);
        expectedPersonDto.setFullName("James Cagney");
        List<Person> actualListOfPersonDto = actorFileRepository.getListOfPeopleByIdMovie("tt0000010");
        assertEquals(Collections.singletonList(expectedPersonDto), actualListOfPersonDto);
    }

    @Test
    void shouldReturnEmptyListOfPersonDtoByIdMovie() {
        List<Person> actualPersonDto = actorFileRepository.getListOfPeopleByIdMovie("tt0000011");
        assertEquals(EMPTY_LIST, actualPersonDto);
    }
}