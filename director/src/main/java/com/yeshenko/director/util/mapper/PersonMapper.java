package com.yeshenko.director.util.mapper;

import com.yeshenko.common.entity.Movie;
import com.yeshenko.common.entity.Person;
import com.yeshenko.director.dto.DirectorDto;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.stream.Collectors;

import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_LIST;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Slf4j
public class PersonMapper {

    /**
     * Mapper from list of people into own director dto.
     *
     * @param people list of Person objects
     * @return list of DirectorDto objects
     */
    public static List<DirectorDto> mapPersonToDirector(List<Person> people) {
        log.debug(METHOD_STARTED_WORK_WITH_LIST, "mapPersonToDirector(List<Person> people)", people.size());

        List<DirectorDto> directors = people.stream()
                .map(person -> {
                    DirectorDto directorDto = new DirectorDto();

                    directorDto.setName(person.getFullName());
                    directorDto.setMovies(person.getMovies()
                            .stream()
                            .map(Movie::getName)
                            .collect(Collectors.toList()));
                    directorDto.setNumberOfFilm(person.getMovies().size());

                    return directorDto;
                })
                .collect(Collectors.toList());

        log.debug(METHOD_EXITED_BY_RETURNING_LIST, "mapPersonToDirector(List<Person> people)", directors.size());
        return directors;
    }
}
