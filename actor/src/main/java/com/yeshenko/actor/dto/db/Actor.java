package com.yeshenko.actor.dto.db;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Getter
@Setter
public class Actor {

    private String fullName;

    public Actor(String fullName) {
        this.fullName = fullName;
    }
}
