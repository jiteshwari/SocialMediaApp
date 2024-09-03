package com.ibm.training.Content.Microservice.exceptions;

public class PostContentException extends RuntimeException {
    public PostContentException(String message) {
        super(message);
    }

    public PostContentException(String message, Throwable cause) {
        super(message, cause);
    }
}