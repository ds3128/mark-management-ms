package com.ds3128.userservice.entities;

import com.ds3128.userservice.enums.Grade;
import com.ds3128.userservice.enums.TeacherStatus;
import com.ds3128.userservice.model.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Teacher extends Personal {

    private String specialization;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    private TeacherStatus teacherStatus;

    private Date arrivalDate;

    @Transient
    private List<Course> courseList;
}
