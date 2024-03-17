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
public class CreateAssignmentDto {

    @NonNull
    private String title;

    private String description;

    private String document;
    @NonNull
    private String weight;
    @NonNull
    private AssignmentStatus status;

    @NonNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate initialDate;
    @NonNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    @NonNull
    private AssignmentType assignmentType;

    @NonNull
    private Long idCourse;
}
