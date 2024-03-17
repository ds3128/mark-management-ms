package com.ds3128.courseservice.modules.notes.dtos;

import com.ds3128.courseservice.modules.notes.enums.MarkStatus;
import com.ds3128.courseservice.modules.notes.enums.Mention;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMarkDto {
    private double value;

    private String observation;

    private Mention mention;

    private MarkStatus markStatus;
}
