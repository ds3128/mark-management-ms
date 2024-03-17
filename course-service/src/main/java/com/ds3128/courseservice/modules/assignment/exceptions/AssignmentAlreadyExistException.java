package com.ds3128.courseservice.modules.assignment.exceptions;

public class AssignmentAlreadyExistException extends RuntimeException {
    public AssignmentAlreadyExistException(String message) {
        super(message);
    }
}
