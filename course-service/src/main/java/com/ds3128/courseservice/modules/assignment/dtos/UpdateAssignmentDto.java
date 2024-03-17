package com.ds3128.courseservice.modules.assignment.dtos;

import com.ds3128.courseservice.modules.assignment.enums.AssignmentStatus;
import com.ds3128.courseservice.modules.assignment.enums.AssignmentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAssignmentDto {
    private String title;

    private String description;

    private String document;

    private String weight;

    private AssignmentStatus status;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate initialDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    private AssignmentType assignmentType;
}
