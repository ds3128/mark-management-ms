package com.ds3128.userservice.mappers;

import com.ds3128.userservice.dtos.StudentDtoRequest;
import com.ds3128.userservice.dtos.TeacherDtoRequest;
import com.ds3128.userservice.entities.Student;
import com.ds3128.userservice.entities.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonalMapperImpl {

    private final ModelMapper modelMapper = new ModelMapper();

    public Student convertToStudent(StudentDtoRequest studentDtoRequest) {
        return modelMapper.map(studentDtoRequest, Student.class);
    }

    public StudentDtoRequest convertToStudentDto(Student student) {
        return modelMapper.map(student, StudentDtoRequest.class);
    }

    public Teacher convertToTeacher(TeacherDtoRequest teacherDtoRequest) {
        return modelMapper.map(teacherDtoRequest, Teacher.class);
    }

    public TeacherDtoRequest convertToTeacherDto(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDtoRequest.class);
    }
}
