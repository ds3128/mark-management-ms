package com.ds3128.fieldstudy.modules.fieldstudy.entities;

import com.ds3128.fieldstudy.common.enums.StatusLevelFieldStudy;
import com.ds3128.fieldstudy.modules.graduate.entities.GraduateLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class FieldStudy {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idField;

    private String code;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatusLevelFieldStudy statusField;

    @OneToMany(mappedBy = "fieldStudy", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<GraduateLevel> graduateLevels;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
