package org.example.exceptions;

public class UserDoesntExistsException  extends RuntimeException {
    public UserDoesntExistsException(String message) {
        super(message);
    }
}
