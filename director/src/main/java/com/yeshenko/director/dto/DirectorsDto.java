package com.yeshenko.director.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Setter
@Getter
public class DirectorsDto {
    List<String> directors;

    public DirectorsDto(List<String> directors) {
        this.directors = directors;
    }
}
