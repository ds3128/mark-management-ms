package com.ds3128.userservice.model;

import com.ds3128.userservice.enums.AssignmentStatus;
import com.ds3128.userservice.enums.AssignmentType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Assignment {
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

    private List<Mark> markList;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
