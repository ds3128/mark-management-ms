package com.ds3128.courseservice.modules.assignment.dtos;

import com.ds3128.courseservice.modules.assignment.enums.AssignmentStatus;
import com.ds3128.courseservice.modules.assignment.enums.AssignmentType;
import com.ds3128.courseservice.modules.course.entities.Course;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AssignmentDtoResponse {
    private Long idAssignment;

    private String title;

    private String description;

    private String document;

    private String weight;

    private AssignmentStatus status;

    private LocalDate initialDate;

    private LocalDate endDate;

    private AssignmentType assignmentType;

    private Course course;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
