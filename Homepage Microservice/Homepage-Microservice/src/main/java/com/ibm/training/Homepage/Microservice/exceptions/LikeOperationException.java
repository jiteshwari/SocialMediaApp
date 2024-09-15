package com.ibm.training.Homepage.Microservice.exceptions;

public class LikeOperationException extends RuntimeException {
    public LikeOperationException(String message) {
        super(message);
    }
}