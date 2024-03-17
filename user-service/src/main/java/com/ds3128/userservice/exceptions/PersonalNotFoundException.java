package com.ds3128.userservice.exceptions;

public class PersonalNotFoundException extends RuntimeException {
    public PersonalNotFoundException(String message) {
        super(message);
    }
}
