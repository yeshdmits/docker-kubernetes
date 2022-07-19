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
public class ActorsOfTheMovie {

    private String title;
    private List<String> actors;

    public ActorsOfTheMovie(String title, List<String> actors) {
        this.title = title;
        this.actors = actors;
    }
}
