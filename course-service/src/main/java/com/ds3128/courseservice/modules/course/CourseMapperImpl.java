package com.ds3128.courseservice.modules.course;

import com.ds3128.courseservice.modules.course.dtos.CourseDtoResponse;
import com.ds3128.courseservice.modules.course.dtos.CourseDtoWithoutTeacher;
import com.ds3128.courseservice.modules.course.dtos.CreateCourseDto;
import com.ds3128.courseservice.modules.course.dtos.UpdateCourseDto;
import com.ds3128.courseservice.modules.course.entities.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CourseMapperImpl {

    private final ModelMapper modelMapper = new ModelMapper();

    public Course fromCourseDto(CreateCourseDto courseDto) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
//        return modelMapper.map(courseDto, Course.class);
        return course;
    }

    public Course fromCourseDtoUpdate(UpdateCourseDto courseDto) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
//        return modelMapper.map(courseDto, Course.class);
        return course;
    }

    public CourseDtoResponse fromCourse(Course course) {
        return modelMapper.map(course, CourseDtoResponse.class);
    }

    public CourseDtoWithoutTeacher fromCourseWithoutTeacher(Course course) {
        return modelMapper.map(course, CourseDtoWithoutTeacher.class);
    }

    public Course fromCourseDtoResponse(CourseDtoResponse courseDtoResponse) {
        return this.modelMapper.map(courseDtoResponse, Course.class);
    }

}
