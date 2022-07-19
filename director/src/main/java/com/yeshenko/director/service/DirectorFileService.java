package com.yeshenko.director.service;

import com.yeshenko.director.dto.DirectorDto;
import com.yeshenko.director.repository.DirectorFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_PARAM;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class DirectorFileService {

    private DirectorFileRepository repository;

    /**
     * Get directors name, number and list all movies. We write everything in the DTO.
     * Database save request status
     *
     * @param name string value
     * @return list of DirectorDto objects
     */
    public List<DirectorDto> findMoviesByDirector(String name) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "findMoviesByDirector(String name)", name);

        List<DirectorDto> moviesByDirector = new ArrayList<>();
        List<String> personId = repository.getPersonIdByName(name);

        personId.forEach(id -> {
            DirectorDto moviesByDirectorDTO = new DirectorDto();
            moviesByDirectorDTO.setName(name);

            List<String> moviesById = repository.readMoviesIdByDirector(id);
            if (CollectionUtils.isNotEmpty(moviesById)) {
                List<String> movies = repository.readAllMoviesNameByMoviesId(moviesById);

                moviesByDirectorDTO.setNumberOfFilm(movies.size());
                moviesByDirectorDTO.setMovies(movies);
                moviesByDirector.add(moviesByDirectorDTO);
            }
        });
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "findMoviesByDirector(String name)", moviesByDirector.size());
        return moviesByDirector;
    }
}
