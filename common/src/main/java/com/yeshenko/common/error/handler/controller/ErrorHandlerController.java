package com.yeshenko.common.error.handler.controller;

import com.yeshenko.common.error.handler.exceptions.DataNotFoundException;
import com.yeshenko.common.error.handler.exceptions.FileNotFoundException;
import com.yeshenko.common.error.handler.exceptions.InvalidParameterException;
import com.yeshenko.common.error.handler.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@ControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    /**
     * Terminate controller which threw FileNotFoundException
     *
     * @param exception FileNotFoundException object
     * @return ResponseEntity with "not found" http status
     */
    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFound(FileNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setInfo("File " + exception.getFileName() + " not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Terminate controller which threw DataNotFoundException exception
     *
     * @return ResponseEntity with "not found" http status
     */
    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFound() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setInfo("There is nothing by this request");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Terminate controller which threw InvalidParameterException exception
     *
     * @return ResponseEntity with "bad request" http status
     */
    @ExceptionHandler(value = InvalidParameterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidParameter() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setInfo("Invalid parameter");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}