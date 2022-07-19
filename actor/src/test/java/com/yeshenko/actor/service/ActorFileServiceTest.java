package com.yeshenko.actor.service;

import com.yeshenko.actor.dto.file.ActorsOfTheMovie;
import com.yeshenko.actor.dto.file.MoviesOfTheActor;
import com.yeshenko.actor.repository.ActorFileRepository;
import com.yeshenko.actor.repository.impl.ActorFileRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ActorFileServiceTest {

    private final String fullName = "James Dean";
    private final String title = "Leaving the Factory";
    private final String MOVIE_FILE_PATH = "../datasets-test/movies-test.tsv";
    private final String PEOPLE_FILE_PATH = "../datasets-test/people-test.tsv";
    private final String ROLE_FILE_PATH = "../datasets-test/roles-test.tsv";

    @Spy
    private ActorFileRepository actorFileRepository = new ActorFileRepositoryImpl(MOVIE_FILE_PATH, ROLE_FILE_PATH, PEOPLE_FILE_PATH);

    @InjectMocks
    private ActorFileService actorFileService;

    @Test
    void shouldFindAllMoviesByActorFullName() {
        List<MoviesOfTheActor> actualListOfMovies = actorFileService.getAllMoviesByActorFullName(fullName);
        assertEquals(1, actualListOfMovies.size());
        assertEquals(fullName, actualListOfMovies.get(0).getFullName());
        assertEquals(title, actualListOfMovies.get(0).getMovies().get(0));
    }

    @Test
    void shouldNotFindAllMoviesByActorFullName() {
        List<MoviesOfTheActor> actualListOfMovies = actorFileService.getAllMoviesByActorFullName("Some person full name");
        assertEquals(0, actualListOfMovies.size());
    }

    @Test
    void shouldFindAllActorsByMovieTitle() {
        List<ActorsOfTheMovie> actualListOfActors = actorFileService.getAllByMovieTitle(title);
        assertEquals(1, actualListOfActors.size());
        assertEquals(title, actualListOfActors.get(0).getTitle());
        assertEquals(fullName, actualListOfActors.get(0).getActors().get(0));
    }

    @Test
    void shouldNotFindAllActorsByMovieTitle() {
        List<ActorsOfTheMovie> actualListOfActors = actorFileService.getAllByMovieTitle("Some movie title");
        assertEquals(0, actualListOfActors.size());
    }
}