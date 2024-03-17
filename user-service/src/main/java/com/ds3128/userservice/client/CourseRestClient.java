package com.ds3128.userservice.client;

import com.ds3128.userservice.model.Course;
import com.ds3128.userservice.model.Mark;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "COURSE-SERVICE")
public interface CourseRestClient {

    @GetMapping("course/findCourseById/{idCourse}")
    @Cacheable("CourseByIdCache")
    Course findCourseById(@PathVariable Long idCourse);


    @GetMapping("mark/findMarkById/{idMark}")
    @Cacheable("markByIdCache")
    Mark findMarkById(@PathVariable Long idMark);

    @GetMapping("/course/findAllByIdUser/{idUser}")
    @Cacheable("allCourseByIdUser")
    List<Course> findAllCourseByIdUser(@PathVariable Long idUser);

    @GetMapping("/mark/findAllMarkById/{idUser}")
    @Cacheable("allMarkByIdUser")
    List<Mark> findAllMarkByIdUser(@PathVariable Long idUser);
}
