package com.yeshenko.movie.util;

import com.yeshenko.movie.dto.MovieDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
public class MovieMapper {

    /**
     * Maps arrays of string to MovieDto
     * Sets 0.0 rating to Dto if rating is null
     * Sets 0 data release to Dto if data release equals \N
     *
     * @param strings array of string values
     * @param rating double value
     * @return MovieDto object
     */
    public static MovieDto mapStringsToMovieDto(String[] strings, Double rating) {

        MovieDto movieDto = new MovieDto();
        movieDto.setName(strings[2]);
        String dataRelease = strings[5];
        movieDto.setId(strings[0]);

        if (dataRelease.equals("\\N")) {
            movieDto.setDataRelease(-1);
        } else {
            movieDto.setDataRelease(Integer.parseInt(strings[5]));
        }

        if (rating == null) {
            movieDto.setRating(-1.0);
        } else {
            movieDto.setRating(rating);
        }

        return movieDto;
    }

    public static List<String> fetchIdsFromMovieDto(List<MovieDto> movies) {
        List<String> ids = new ArrayList<>();

        movies.forEach(movie -> {
            ids.add(movie.getId());
        });

        return ids;
    }
}