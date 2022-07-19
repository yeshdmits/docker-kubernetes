package com.yeshenko.movie.controller;

import com.yeshenko.common.entity.Request;
import com.yeshenko.common.entity.Result;
import com.yeshenko.common.error.handler.exceptions.DataNotFoundException;
import com.yeshenko.common.error.handler.exceptions.InvalidParameterException;
import com.yeshenko.common.service.RequestJpaService;
import com.yeshenko.common.util.RequestMapper;
import com.yeshenko.movie.activemq.MovieProducer;
import com.yeshenko.movie.dto.MovieDto;
import com.yeshenko.movie.service.MovieFileService;
import com.yeshenko.movie.service.MovieJpaService;
import com.yeshenko.movie.util.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

import static com.yeshenko.common.Constants.PARAM_IS_BLANK;
import static com.yeshenko.common.entity.RequestType.MOVIE;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class MovieController {

    private MovieFileService fileService;
    private MovieJpaService jpaService;
    private RequestJpaService requestService;
    private MovieProducer producer;

    /**
     * Gets the best movies by total, which wanted.
     * From file.
     *
     * @param total       integer value
     * @param httpRequest with username for movie microservice
     * @return list of MovieDto objects
     */
    @GetMapping(path = "/best")
    public List<MovieDto> getBestMovies(@RequestParam Integer total, HttpServletRequest httpRequest) {
        Request defaultRequest = requestService.create(RequestMapper.mapDefaultRequest(httpRequest, MOVIE));

        if (total <= 0) {
            log.warn("Total is less than zero or equals zero");
            throw new InvalidParameterException();
        }

        List<MovieDto> bestMovies = fileService.findTheBestMovies(total);
        updateRequest(defaultRequest);
        return bestMovies;
    }

    /**
     * Gets all movies by period.
     * From file.
     *
     * @param fromYear    integer value
     * @param toYear      integer value
     * @param httpRequest with username for movie microservice
     * @return list of MovieDto objects
     */
    @GetMapping(path = "/period")
    public List<MovieDto> getMoviesByPeriod(@RequestParam Integer fromYear, @RequestParam Integer toYear,
                                            HttpServletRequest httpRequest) {

        if (isNotYear(fromYear) || isNotYear(toYear)) {
            log.warn("fromYear or toYear is invalid");
            throw new InvalidParameterException();
        }

        Request defaultRequest = requestService.create(RequestMapper.mapDefaultRequest(httpRequest, MOVIE));
        List<MovieDto> moviesByPeriod = fileService.findMoviesByPeriod(fromYear, toYear);
        updateRequest(defaultRequest);
        return moviesByPeriod;
    }

    /**
     * Gets all movies.
     * From database.
     *
     * @param httpRequest with username for movie microservice
     * @return list of MovieDto objects
     */
    @GetMapping
    public List<MovieDto> getMovies(HttpServletRequest httpRequest) {
        Request defaultRequest = requestService.create(RequestMapper.mapDefaultRequest(httpRequest, MOVIE));
        List<MovieDto> allMovies = jpaService.getAllMovies();

        updateRequest(defaultRequest);

        if (CollectionUtils.isEmpty(allMovies)) {
            log.warn("There is no movies in database");
            throw new DataNotFoundException();
        }
        return allMovies;
    }

    /**
     * Gets all movies by name.
     * From database, if it exists.
     * From file if movie by this name is absent in database.
     * Sends movies ids to queue.
     *
     * @param name        movie name for searching
     * @param httpRequest with username for movie microservice
     * @return list of MovieDto objects
     */
    @GetMapping(path = "/name")
    public List<MovieDto> getMoviesByName(@RequestParam String name, HttpServletRequest httpRequest) {
        Request defaultRequest = requestService.create(RequestMapper.mapDefaultRequest(httpRequest, MOVIE));
        List<MovieDto> moviesByNameFromDatabase = jpaService.getMoviesByName(name);

        if (StringUtils.isBlank(name)) {
            log.warn(PARAM_IS_BLANK);
            throw new InvalidParameterException();
        }

        if (CollectionUtils.isEmpty(moviesByNameFromDatabase)) {
            List<MovieDto> moviesByNameFromFile = fileService.findMoviesByName(name);

            if (CollectionUtils.isEmpty(moviesByNameFromFile)) {
                updateRequest(defaultRequest);
                log.warn("There is no movies in file by name {}", name);
                throw new DataNotFoundException();
            }

            jpaService.saveRequestedMovies(moviesByNameFromFile);
            producer.sendMoviesIds(MovieMapper.fetchIdsFromMovieDto(moviesByNameFromFile));

            updateRequest(defaultRequest);
            return moviesByNameFromFile;
        }
        updateRequest(defaultRequest);
        return moviesByNameFromDatabase;
    }

    /**
     * Change result on SUCCESS and request save to database.
     *
     * @param request with username for director microservice
     */
    private void updateRequest(Request request) {
        request.setResult(Result.SUCCESS);
        requestService.create(request);
    }

    /**
     * Checks if it is year
     *
     * @param year integer value
     * @return boolean value
     */
    private boolean isNotYear(Integer year) {
        if (year < 1895 || year > LocalDate.now().getYear()) {
            return true;
        }
        return false;
    }
}
