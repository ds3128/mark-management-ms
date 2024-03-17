package com.ds3128.courseservice.modules.assignment;

import com.ds3128.courseservice.modules.assignment.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    boolean existsByTitle(String title);
}
