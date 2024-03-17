package com.ds3128.courseservice.modules.course.dtos;

import com.ds3128.courseservice.modules.assignment.entities.Assignment;
import com.ds3128.courseservice.modules.models.GraduateLevel;
import com.ds3128.courseservice.modules.models.Teacher;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDtoResponse {
    private Long idCourse;
    private String title;
    private String description;
    private int totalHours;
    private int creditNumber;

    private String idLevel;
    private GraduateLevel graduateLevel;

    private Long idUser;
    private Teacher teacher;

    private List<Assignment> assignmentList;
}
