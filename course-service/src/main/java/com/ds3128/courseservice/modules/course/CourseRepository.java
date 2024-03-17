package com.ds3128.courseservice.modules.course;

import com.ds3128.courseservice.modules.course.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByTitle(String title);

    List<Course> findAllByIdUser(Long idUser);
}
