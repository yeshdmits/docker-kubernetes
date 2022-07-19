package com.yeshenko.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Getter
@Setter
public class MovieDto {

    @JsonIgnore
    private String id;
    private String name;
    private Integer dataRelease;
    private Double rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDto movieDto = (MovieDto) o;
        return Objects.equals(dataRelease, movieDto.dataRelease) && Objects.equals(rating, movieDto.rating) && Objects.equals(name, movieDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dataRelease, rating);
    }
}