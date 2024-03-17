package com.ds3128.userservice.exceptions;

public class PersonalAlreadyExistException extends RuntimeException {
    public PersonalAlreadyExistException(String message) {
        super(message);
    }
}
