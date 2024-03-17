package com.ds3128.userservice.entities;

import com.ds3128.userservice.enums.StudentStatus;
import com.ds3128.userservice.model.Mark;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
public class Student extends Personal {

    @Column(unique = true)
    private String idStudent;

    @Enumerated(EnumType.STRING)
    private StudentStatus studentStatus;

    @Transient
    private List<Mark> markList;
}
