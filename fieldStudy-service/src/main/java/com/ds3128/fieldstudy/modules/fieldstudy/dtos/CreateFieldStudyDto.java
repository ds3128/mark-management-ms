package com.ds3128.fieldstudy.modules.fieldstudy.dtos;

import com.ds3128.fieldstudy.common.enums.StatusLevelFieldStudy;
import lombok.*;
import org.springframework.lang.NonNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFieldStudyDto {

    @NonNull
    private String code;

    @NonNull
    private String name;

    private String description;

    @NonNull
    private StatusLevelFieldStudy statusField;
}
