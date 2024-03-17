package com.ds3128.courseservice.modules.models;

import com.ds3128.courseservice.modules.models.enums.Grade;
import com.ds3128.courseservice.modules.models.enums.TeacherStatus;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends Personal {

    private String specialization;

    private Grade grade;

    private TeacherStatus teacherStatus;

    private Date arrivalDate;
}
