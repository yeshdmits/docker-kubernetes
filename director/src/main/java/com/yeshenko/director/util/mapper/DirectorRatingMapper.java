package com.yeshenko.director.util.mapper;

import com.yeshenko.director.dto.DirectorDto;
import com.yeshenko.director.dto.DirectorDtoRating;
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
public class DirectorRatingMapper {

    /**
     * Mapper from list of own director dto into another dto DirectorDtoRating.
     *
     * @param directorDto list of DirectorDto objects
     * @return list of DirectorDtoRating objects
     */
    public static List<DirectorDtoRating> mapToDirectorByRating(List<DirectorDto> directorDto) {
        log.debug(METHOD_STARTED_WORK_WITH_LIST, "mapToDirectorByRating(List<DirectorDto> directorDto)",
                directorDto.size());

        List<DirectorDtoRating> directors = directorDto.stream()
                .map(person -> {
                    DirectorDtoRating directorDtoRating = new DirectorDtoRating();

                    directorDtoRating.setName(person.getName());
                    directorDtoRating.setNumberOfFilm(person.getNumberOfFilm());
                    return directorDtoRating;
                })
                .collect(Collectors.toList());

        log.debug(METHOD_EXITED_BY_RETURNING_LIST, "mapToDirectorByRating(List<DirectorDto> directorDto)",
                directors.size());
        return directors;
    }
}
