package com.ds3128.fieldstudy.modules.fieldstudy.dtos;

import com.ds3128.fieldstudy.common.enums.StatusLevelFieldStudy;
import com.ds3128.fieldstudy.modules.graduate.entities.GraduateLevel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldStudyDtoResponse {

    private String idField;

    private String code;

    private String name;

    private String description;

    private StatusLevelFieldStudy statusField;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<GraduateLevel> graduateLevels;
}
