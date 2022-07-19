package com.yeshenko.actor.service;

import com.yeshenko.actor.repository.MovieJpaRepository;
import com.yeshenko.common.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_OBJECT;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_PARAM;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class MovieJpaService {

    private MovieJpaRepository movieJpaRepository;

    /**
     * Get movie by id from database.
     *
     * @param id string value
     * @return Movie object
     */
    @Transactional(readOnly = true)
    public Movie getById(String id) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "getById(String id)", id);
        Movie movie = movieJpaRepository.findMovieById(id);
        log.info(METHOD_EXITED_BY_RETURNING_OBJECT, "getById(String id)");
        return movie;
    }
}
