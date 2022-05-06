package com.deloitte.baseapp.commons;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.UnexpectedTypeException;

@RestControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnexpectedTypeException.class)
    private MessageResponse handleUnexpectedTypeException(UnexpectedTypeException ex){
        return MessageResponse.ErrorWithCode(ex.getMessage(), 400);
    }
}
