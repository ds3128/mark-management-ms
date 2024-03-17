package com.ds3128.userservice.repositories;

import com.ds3128.userservice.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
