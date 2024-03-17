package com.ds3128.courseservice.modules.models;

import com.ds3128.courseservice.modules.models.enums.StudentStatus;
import com.ds3128.courseservice.modules.notes.entities.Mark;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Student extends Personal {

    private String idStudent;

    private StudentStatus studentStatus;

    private List<Mark> markList;
}
