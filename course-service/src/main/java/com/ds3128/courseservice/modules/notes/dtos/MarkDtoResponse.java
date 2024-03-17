package com.ds3128.courseservice.modules.notes.dtos;

import com.ds3128.courseservice.modules.assignment.entities.Assignment;
import com.ds3128.courseservice.modules.models.Student;
import com.ds3128.courseservice.modules.notes.enums.MarkStatus;
import com.ds3128.courseservice.modules.notes.enums.Mention;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MarkDtoResponse {
    private Long idMark;

    private Long idUser;

    private double value;

    private String observation;

    private Mention mention;

    private MarkStatus markStatus;

    private Assignment assignment;

    private Student student;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
