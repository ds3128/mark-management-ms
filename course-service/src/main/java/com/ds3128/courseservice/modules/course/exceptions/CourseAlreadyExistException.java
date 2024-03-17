package com.ds3128.courseservice.modules.course.exceptions;

public class CourseAlreadyExistException extends RuntimeException {
    public CourseAlreadyExistException(String message) {
        super(message);
    }
}
