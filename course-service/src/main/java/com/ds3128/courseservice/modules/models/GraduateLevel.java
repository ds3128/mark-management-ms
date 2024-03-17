package com.ds3128.courseservice.modules.models;

import com.ds3128.courseservice.modules.models.enums.StatusLevelFieldStudy;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GraduateLevel {
    private String idLevel;

    private String code;

    private String name;

    private String description;

    private StatusLevelFieldStudy statusLevel;

    private FieldStudy fieldStudy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
