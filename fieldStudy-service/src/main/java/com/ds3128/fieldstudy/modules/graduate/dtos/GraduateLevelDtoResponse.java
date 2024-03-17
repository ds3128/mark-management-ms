package com.ds3128.fieldstudy.modules.graduate.dtos;

import com.ds3128.fieldstudy.common.enums.StatusLevelFieldStudy;
import com.ds3128.fieldstudy.modules.fieldstudy.entities.FieldStudy;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GraduateLevelDtoResponse {

    private String idLevel;

    private String code;

    private String name;

    private String description;

    private StatusLevelFieldStudy statusLevel;

    private FieldStudy fieldStudy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
