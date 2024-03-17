package com.ds3128.userservice.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Course {
    private Long idCourse;

    private String title;
    private String description;
    private int totalHours;
    private int creditNumber;

    private List<Assignment> assignmentList;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
