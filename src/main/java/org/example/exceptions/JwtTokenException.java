package org.example.exceptions;

public class JwtTokenException extends RuntimeException {

    public JwtTokenException(String message) {
        super(message);
    }
}