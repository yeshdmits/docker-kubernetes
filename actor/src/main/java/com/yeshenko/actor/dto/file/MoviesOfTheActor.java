package com.yeshenko.actor.dto.file;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Getter
@Setter
public class MoviesOfTheActor {

    private String fullName;
    private List<String> movies;

    public MoviesOfTheActor(String fullName, List<String> movies) {
        this.fullName = fullName;
        this.movies = movies;
    }
}
