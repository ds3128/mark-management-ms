package com.ds3128.courseservice.modules.course.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCourseDto {
    private String title;
    private String description;
    private int totalHours;
    private int creditNumber;
    private Long idUser;

    private String idLevel;
}
