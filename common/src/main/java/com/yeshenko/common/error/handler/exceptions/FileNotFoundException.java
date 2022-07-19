package com.yeshenko.common.error.handler.exceptions;

import lombok.Getter;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Getter
public class FileNotFoundException extends RuntimeException {

    private final String fileName;

    public FileNotFoundException(String fileName) {
        this.fileName = fileName;
    }
}