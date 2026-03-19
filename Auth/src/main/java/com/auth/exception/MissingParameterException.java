package com.auth.exception;

public class MissingParameterException extends RuntimeException {

    public MissingParameterException(String message) {
        super(message);
    }
}