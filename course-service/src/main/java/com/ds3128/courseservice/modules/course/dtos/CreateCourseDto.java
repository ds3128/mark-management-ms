package com.ds3128.courseservice.modules.course.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourseDto {
    @NotNull
    private String title;

    private String description;

    @NotNull
    private int totalHours;

    @NotNull
    private int creditNumber;

    private Long idUser;

    private String idLevel;
}
