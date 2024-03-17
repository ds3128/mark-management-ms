package com.ds3128.courseservice.modules.course.services;

import com.ds3128.courseservice.common.dtos.PaginateDTO;
import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.course.dtos.CourseDtoResponse;
import com.ds3128.courseservice.modules.course.dtos.CourseDtoWithoutTeacher;
import com.ds3128.courseservice.modules.course.dtos.CreateCourseDto;
import com.ds3128.courseservice.modules.course.dtos.UpdateCourseDto;
import com.ds3128.courseservice.modules.course.entities.Course;

import java.util.List;

public interface CourseService {

    CourseDtoResponse create(CreateCourseDto courseDto);

    CourseDtoResponse update(Long idCourse, UpdateCourseDto courseDto);

    CourseDtoResponse findOneById(Long idCourse);

    List<CourseDtoWithoutTeacher> findAllByIdUser(Long idUser);

    Course findCourseById(Long idCourse);

    PaginateResponse<CourseDtoResponse> findAll(PaginateDTO paginateDTO);

    CourseDtoResponse delete(Long idCourse);
}
