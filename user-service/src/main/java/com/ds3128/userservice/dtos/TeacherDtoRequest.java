package com.ds3128.userservice.dtos;

import com.ds3128.userservice.enums.Grade;
import com.ds3128.userservice.enums.TeacherStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TeacherDtoRequest {

    private String firstName;

    private String lastName;

    private String email;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

    private String phoneNumber;

    private String specialization;

    private Grade grade;

    private TeacherStatus teacherStatus;

    private Date arrivalDate;
}
