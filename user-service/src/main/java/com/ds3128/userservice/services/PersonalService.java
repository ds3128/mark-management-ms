package com.ds3128.userservice.services;

import com.ds3128.userservice.common.dtos.PaginateDTO;
import com.ds3128.userservice.common.types.PaginateResponse;
import com.ds3128.userservice.dtos.StudentDtoRequest;
import com.ds3128.userservice.dtos.TeacherDtoRequest;
import com.ds3128.userservice.entities.Student;
import com.ds3128.userservice.entities.Teacher;

public interface PersonalService {
    Student createStudent(StudentDtoRequest studentDtoRequest);

    Teacher createTeacher(TeacherDtoRequest teacherDtoRequest);

    Student updateStudent(Long idUser, StudentDtoRequest studentDtoRequest);

    Teacher updateTeacher(Long idUser, TeacherDtoRequest teacherDtoRequest);

    Student findOneStudentById(Long idUser);

    Student findOneStudent(Long idUser);

    Teacher findOneTeacher(Long idUser);

    Teacher findOneTeacherById(Long idUser);

    PaginateResponse<Teacher> findAllTeacher(PaginateDTO paginateDTO);

    PaginateResponse<Student> findAllStudent(PaginateDTO paginateDTO);

    Student deleteStudentById(Long idUser);

    Teacher deleteTeacherById(Long idUser);
}
