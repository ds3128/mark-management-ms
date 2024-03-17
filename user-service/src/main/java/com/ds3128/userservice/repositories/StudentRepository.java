package com.ds3128.userservice.repositories;

import com.ds3128.userservice.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
