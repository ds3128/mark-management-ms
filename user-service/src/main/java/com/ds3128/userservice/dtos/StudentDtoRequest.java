package com.ds3128.userservice.dtos;

import com.ds3128.userservice.enums.StudentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDtoRequest {

    private String firstName;

    private String lastName;

    private String email;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

    private String phoneNumber;

    private StudentStatus studentStatus;
}
