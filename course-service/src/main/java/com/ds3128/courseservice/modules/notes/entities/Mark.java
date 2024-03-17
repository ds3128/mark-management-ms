package com.ds3128.courseservice.modules.notes.entities;

import com.ds3128.courseservice.modules.assignment.entities.Assignment;
import com.ds3128.courseservice.modules.models.Student;
import com.ds3128.courseservice.modules.notes.enums.MarkStatus;
import com.ds3128.courseservice.modules.notes.enums.Mention;
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
public class Mark {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMark;

    private double value;

    private String observation;

    @Enumerated(EnumType.STRING)
    private Mention mention;

    @Enumerated(EnumType.STRING)
    private MarkStatus markStatus;

    @ManyToOne
    private Assignment assignment;
    private Long idAssignment;

    @Transient
    private Student student;
    private Long idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
