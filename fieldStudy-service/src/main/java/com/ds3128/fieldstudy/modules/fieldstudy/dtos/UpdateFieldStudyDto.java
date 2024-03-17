package com.ds3128.fieldstudy.modules.fieldstudy.dtos;

import com.ds3128.fieldstudy.common.enums.StatusLevelFieldStudy;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateFieldStudyDto {

    private String code;

    private String name;

    private String description;

    private StatusLevelFieldStudy statusField;
}
