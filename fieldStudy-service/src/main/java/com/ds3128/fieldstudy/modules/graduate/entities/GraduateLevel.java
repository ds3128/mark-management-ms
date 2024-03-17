package com.ds3128.fieldstudy.modules.graduate.entities;

import com.ds3128.fieldstudy.common.enums.StatusLevelFieldStudy;
import com.ds3128.fieldstudy.modules.fieldstudy.entities.FieldStudy;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GraduateLevel {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idLevel;

    private String code;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatusLevelFieldStudy statusLevel;

    @ManyToOne
    private FieldStudy fieldStudy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
