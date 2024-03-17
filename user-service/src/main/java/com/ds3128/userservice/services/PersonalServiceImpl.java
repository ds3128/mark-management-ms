package com.ds3128.userservice.services;

import com.ds3128.userservice.client.CourseRestClient;
import com.ds3128.userservice.common.dtos.PaginateDTO;
import com.ds3128.userservice.common.types.PaginateResponse;
import com.ds3128.userservice.dtos.StudentDtoRequest;
import com.ds3128.userservice.dtos.TeacherDtoRequest;
import com.ds3128.userservice.entities.Personal;
import com.ds3128.userservice.entities.Student;
import com.ds3128.userservice.entities.Teacher;
import com.ds3128.userservice.exceptions.PersonalAlreadyExistException;
import com.ds3128.userservice.exceptions.PersonalNotFoundException;
import com.ds3128.userservice.mappers.PersonalMapperImpl;
import com.ds3128.userservice.model.Course;
import com.ds3128.userservice.model.Mark;
import com.ds3128.userservice.repositories.PersonalRepository;
import com.ds3128.userservice.repositories.StudentRepository;
import com.ds3128.userservice.repositories.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class PersonalServiceImpl implements PersonalService {

    private final PersonalRepository personalRepository;
    private final PersonalMapperImpl personalMapper;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final CourseRestClient courseRestClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalServiceImpl.class);

    public PersonalServiceImpl(PersonalRepository personalRepository, PersonalMapperImpl personalMapper, TeacherRepository teacherRepository, StudentRepository studentRepository, CourseRestClient courseRestClient) {
        this.personalRepository = personalRepository;
        this.personalMapper = personalMapper;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.courseRestClient = courseRestClient;
    }

    @Override
    public Student createStudent(StudentDtoRequest studentDtoRequest) {
        if (!studentDtoRequest.getEmail().contains(".")) {
            throw new RuntimeException("Invalid email");
        }

        if (!studentDtoRequest.getEmail().contains("@")){
            throw new RuntimeException("Invalid email");
        }

        Optional<Personal> personalOptional = this.personalRepository.findByEmail(studentDtoRequest.getEmail());

        if (personalOptional.isPresent()) {
            throw new PersonalAlreadyExistException("this email already exist");
        }

        if (studentDtoRequest.getLastName().isBlank() || studentDtoRequest.getLastName().isEmpty()) {
            throw new IllegalArgumentException("LastName can't be null");
        }
        if (studentDtoRequest.getPhoneNumber().isBlank() || studentDtoRequest.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number can't be null");
        }
        Student student = this.personalMapper.convertToStudent(studentDtoRequest);
        student.setIdStudent(UUID.randomUUID().toString());

        return this.personalRepository.save(student);
    }

    @Override
    public Teacher createTeacher(TeacherDtoRequest teacherDtoRequest) {
        if (!teacherDtoRequest.getEmail().contains(".")) {
            throw new RuntimeException("Invalid email");
        }

        if (!teacherDtoRequest.getEmail().contains("@")){
            throw new RuntimeException("Invalid email");
        }

        Optional<Personal> personalOptional = this.personalRepository.findByEmail(teacherDtoRequest.getEmail());

        if (personalOptional.isPresent()) {
            throw new PersonalAlreadyExistException("this email already exist");
        }

        if (teacherDtoRequest.getLastName().isBlank() || teacherDtoRequest.getLastName().isEmpty()) {
            throw new IllegalArgumentException("LastName can't be null");
        }
        if (teacherDtoRequest.getPhoneNumber().isBlank() || teacherDtoRequest.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number can't be null");
        }

        // Convert teacher dto to entity
        Teacher teacher = this.personalMapper.convertToTeacher(teacherDtoRequest);

        // Return teacher converting to teacher dto
        return this.personalRepository.save(teacher);
    }

    @Override
    public Student updateStudent(Long idUser, StudentDtoRequest studentDtoRequest) {
        Student studentExist = this.studentRepository.findById(idUser).orElseThrow(() -> new PersonalNotFoundException("student not found"));

        if (studentDtoRequest.getPhoneNumber() == null || studentDtoRequest.getPhoneNumber().isEmpty()) {
            studentDtoRequest.setPhoneNumber(studentExist.getPhoneNumber());
        }
        if (studentDtoRequest.getLastName() == null || studentDtoRequest.getLastName().isEmpty()) {
            studentDtoRequest.setLastName(studentExist.getLastName());
        }
        if (studentDtoRequest.getFirstName() == null || studentDtoRequest.getFirstName().isEmpty()) {
            studentDtoRequest.setFirstName(studentExist.getFirstName());
        }
        if (studentDtoRequest.getEmail() == null || studentDtoRequest.getEmail().isEmpty()) {
            studentDtoRequest.setEmail(studentExist.getEmail());
        }

        Student student = this.personalMapper.convertToStudent(studentDtoRequest);
        student.setIdUser(idUser);

        return personalRepository.save(student);
    }

    @Override
    public Teacher updateTeacher(Long idUser, TeacherDtoRequest teacherDtoRequest) {

        Teacher teacherExist = this.teacherRepository.findById(idUser).orElseThrow(() -> new PersonalNotFoundException("Teacher not found"));

        if (teacherDtoRequest.getPhoneNumber() == null || teacherDtoRequest.getPhoneNumber().isEmpty()) {
            teacherDtoRequest.setPhoneNumber(teacherExist.getPhoneNumber());
        }
        if (teacherDtoRequest.getLastName() == null || teacherDtoRequest.getLastName().isEmpty()) {
            teacherDtoRequest.setLastName(teacherExist.getLastName());
        }
        if (teacherDtoRequest.getFirstName() == null || teacherDtoRequest.getFirstName().isEmpty()) {
            teacherDtoRequest.setFirstName(teacherExist.getFirstName());
        }
        if (teacherDtoRequest.getEmail() == null || teacherDtoRequest.getEmail().isEmpty()) {
            teacherDtoRequest.setEmail(teacherExist.getEmail());
        }
        if (teacherDtoRequest.getSpecialization() == null || teacherDtoRequest.getSpecialization().isEmpty()) {
            teacherDtoRequest.setSpecialization(teacherExist.getSpecialization());
        }

        Teacher teacher = this.personalMapper.convertToTeacher(teacherDtoRequest);
        teacher.setIdUser(idUser);

        return this.personalRepository.save(teacher);
    }

    @Override
    public Student findOneStudentById(Long idUser) {

        Student student = this.studentRepository.findById(idUser).orElseThrow(() -> new PersonalNotFoundException("student not found"));

        List<Mark> markList;

        try {
            markList = this.courseRestClient.findAllMarkByIdUser(idUser);
        } catch (Exception e) {
            LOGGER.error("An error occurred during finding student marks {}", e.getMessage(), e);
            throw new RuntimeException("User not found");
        }

        student.setMarkList(markList);

        return student;
    }

    @Override
    // used for others microservice
    public Student findOneStudent(Long idUser) {
        return this.studentRepository.findById(idUser).get();
    }

    @Override
    // used for others microservice
    public Teacher findOneTeacher(Long idUser) {
        return this.teacherRepository.findById(idUser).get();
    }

    @Override
    public Teacher findOneTeacherById(Long idUser) {

        Teacher teacher = this.teacherRepository.findById(idUser).orElseThrow(() -> new PersonalNotFoundException("Teacher not found"));

        List<Course> courseList;

        try {
            courseList = this.courseRestClient.findAllCourseByIdUser(idUser);
        } catch (Exception e) {
            LOGGER.error("And error occurred during getting teacher info : {}", e.getMessage(), e);

            throw new RuntimeException("Teacher not found");
        }

        teacher.setCourseList(courseList);

        return teacher;
    }

    @Override
    public PaginateResponse<Teacher> findAllTeacher(PaginateDTO paginateDTO) {
        PageRequest pageRequest = PageRequest.of(paginateDTO.getOffset(), paginateDTO.getLimit(), Sort.by("specialization").ascending());
        Page<Teacher> teacherPage = this.teacherRepository.findAll(pageRequest);

        List<Teacher> teacherDtoRequestList = teacherPage.stream().toList();

        long totalElements = teacherPage.getTotalElements();
        long totalPage = teacherPage.getTotalPages();
        long currentPage = teacherPage.getNumber();
        boolean hasMore = paginateDTO.getOffset() + paginateDTO.getLimit() < totalElements;
        return new PaginateResponse<>(totalElements, hasMore, teacherDtoRequestList, currentPage, totalPage);
    }

    @Override
    public PaginateResponse<Student> findAllStudent(PaginateDTO paginateDTO) {
        PageRequest pageRequest = PageRequest.of(paginateDTO.getOffset(), paginateDTO.getLimit(), Sort.by("lastName").ascending());
        Page<Student> teacherPage = this.studentRepository.findAll(pageRequest);

        List<Student> studentDtoRequestList = teacherPage.stream().toList();

        long totalElements = teacherPage.getTotalElements();
        long totalPage = teacherPage.getTotalPages();
        long currentPage = teacherPage.getNumber();
        boolean hasMore = paginateDTO.getOffset() + paginateDTO.getLimit() < totalElements;
        return new PaginateResponse<>(totalElements, hasMore, studentDtoRequestList, currentPage, totalPage);
    }

    @Override
    public Student deleteStudentById(Long idUser) {
        Student studentExist = this.studentRepository.findById(idUser).orElseThrow(() -> new PersonalNotFoundException("student not found"));

        this.studentRepository.deleteById(idUser);

        return studentExist;
    }

    @Override
    public Teacher deleteTeacherById(Long idUser) {
        Teacher teacherExist = this.teacherRepository.findById(idUser).orElseThrow(() -> new PersonalNotFoundException("Teacher not found"));

        this.teacherRepository.deleteById(idUser);

        return teacherExist;
    }
}
