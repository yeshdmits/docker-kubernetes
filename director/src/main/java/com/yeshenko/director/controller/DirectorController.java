package com.yeshenko.director.controller;

import com.yeshenko.common.entity.Request;
import com.yeshenko.common.service.RequestJpaService;
import com.yeshenko.common.util.RequestMapper;
import com.yeshenko.director.dto.DirectorDto;
import com.yeshenko.director.dto.DirectorDtoRating;
import com.yeshenko.director.dto.DirectorsDto;
import com.yeshenko.director.service.DirectorFileService;
import com.yeshenko.director.service.DirectorJpaService;
import com.yeshenko.director.util.Validator;
import com.yeshenko.director.util.mapper.DirectorRatingMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.yeshenko.common.entity.RequestType.DIRECTOR;
import static com.yeshenko.common.entity.Result.SUCCESS;


/**
 * @author Yeshenko Dmitriy
 * @version 1.1
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@ComponentScan(basePackages = {"com.yeshenko.common"})
public class DirectorController {

    private DirectorFileService directorFileService;
    private DirectorJpaService directorJpaService;
    private RequestJpaService requestJpaService;

    /**
     * Get all movies by name director.
     * Search by file.
     *
     * @param name string value
     * @param request with username for director microservice
     * @return list of DirectorSto objects
     */
    @GetMapping("/movies")
    public List<DirectorDto> getAllMovies(@RequestParam("name") String name, HttpServletRequest request) {
        Validator.checkInputParameter(name);
        Request defaultRequest = requestJpaService.create(RequestMapper.mapDefaultRequest(request, DIRECTOR));
        List<DirectorDto> moviesByDirector = directorFileService.findMoviesByDirector(name);
        Validator.checkOnEmpty(moviesByDirector, "moviesByDirector");
        updateRequest(defaultRequest);
        return moviesByDirector;
    }

    /**
     * Get all directors.
     * Search by database.
     *
     * @param request with username for director microservice
     * @return list of string values
     */
    @GetMapping
    public DirectorsDto getAllDirectors(HttpServletRequest request) {
        Request defaultRequest = requestJpaService.create(RequestMapper.mapDefaultRequest(request, DIRECTOR));
        List<DirectorDto> allDirectors = directorJpaService.findAllDirectors();
        Validator.checkOnEmpty(allDirectors, "allDirectors");
        updateRequest(defaultRequest);
        return new DirectorsDto(allDirectors.stream().map(DirectorDto::getName).collect(Collectors.toList()));
    }

    /**
     * Get rating all directors.
     * Search by database.
     *
     * @param request with username for director microservice
     * @return list of DirectorDtoRating objects
     */
    @GetMapping("/rating")
    public List<DirectorDtoRating> getAllDirectorsRating(HttpServletRequest request) {
        Request defaultRequest = requestJpaService.create(RequestMapper.mapDefaultRequest(request, DIRECTOR));
        List<DirectorDto> directorsByRating = directorJpaService.findDirectorsByRating();
        Validator.checkOnEmpty(directorsByRating, "directorsByRating");
        updateRequest(defaultRequest);
        return DirectorRatingMapper.mapToDirectorByRating(directorsByRating);
    }

    /**
     * Change result on SUCCESS and request save to database.
     *
     * @param request with username for director microservice
     */
    private void updateRequest(Request request) {
        request.setResult(SUCCESS);
        requestJpaService.create(request);
    }
}
