package com.ds3128.courseservice.modules.assignment.entities;

import com.ds3128.courseservice.modules.assignment.enums.AssignmentType;
import com.ds3128.courseservice.modules.assignment.enums.AssignmentStatus;
import com.ds3128.courseservice.modules.course.entities.Course;
import com.ds3128.courseservice.modules.notes.entities.Mark;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAssignment;

    private String title;

    private String description;

    private String document;

    private String weight;
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    private LocalDate initialDate;

    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private AssignmentType assignmentType;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Mark> markList;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
