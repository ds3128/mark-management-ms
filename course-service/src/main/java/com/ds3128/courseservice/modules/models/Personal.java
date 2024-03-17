package com.ds3128.courseservice.modules.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public abstract class Personal {

    private Long idUser;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate birthday;

    private String phoneNumber;
}
