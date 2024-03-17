package com.ds3128.courseservice.modules.models;

import com.ds3128.courseservice.modules.models.enums.StatusLevelFieldStudy;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldStudy {
    private String idField;

    private String code;

    private String name;

    private String description;

    private StatusLevelFieldStudy statusField;

//    private List<GraduateLevel> graduateLevels;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
