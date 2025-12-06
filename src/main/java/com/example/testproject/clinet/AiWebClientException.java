package com.example.testproject.clinet;

public class AiWebClientException extends RuntimeException {
    public AiWebClientException(String message) {
        super(message);
    }

    public AiWebClientException(String message, Throwable cause) {
        super(message, cause);
    }
}