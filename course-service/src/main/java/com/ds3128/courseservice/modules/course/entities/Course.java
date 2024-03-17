package com.ds3128.courseservice.modules.course.entities;

import com.ds3128.courseservice.modules.assignment.entities.Assignment;
import com.ds3128.courseservice.modules.models.GraduateLevel;
import com.ds3128.courseservice.modules.models.Teacher;
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
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCourse;

    private String title;
    private String description;
    private int totalHours;
    private int creditNumber;

    @Transient
    private Teacher teacher;
    private Long idUser;

    @Transient
    private GraduateLevel graduateLevel;
    private String idLevel;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Assignment> assignmentList;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
