package com.ds3128.courseservice.modules.course.services;

import com.ds3128.courseservice.common.dtos.PaginateDTO;
import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.client.FieldStudyRestClient;
import com.ds3128.courseservice.modules.client.UserRestClient;
import com.ds3128.courseservice.modules.course.CourseMapperImpl;
import com.ds3128.courseservice.modules.course.CourseRepository;
import com.ds3128.courseservice.modules.course.dtos.CourseDtoResponse;
import com.ds3128.courseservice.modules.course.dtos.CourseDtoWithoutTeacher;
import com.ds3128.courseservice.modules.course.dtos.CreateCourseDto;
import com.ds3128.courseservice.modules.course.dtos.UpdateCourseDto;
import com.ds3128.courseservice.modules.course.entities.Course;
import com.ds3128.courseservice.modules.course.exceptions.CourseAlreadyExistException;
import com.ds3128.courseservice.modules.course.exceptions.CourseNotFoundException;
import com.ds3128.courseservice.modules.models.GraduateLevel;
import com.ds3128.courseservice.modules.models.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapperImpl courseMapper;
    private final UserRestClient userRestClient;
    private final FieldStudyRestClient fieldStudyRestClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapperImpl courseMapper, UserRestClient userRestClient, FieldStudyRestClient fieldStudyRestClient) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.userRestClient = userRestClient;
        this.fieldStudyRestClient = fieldStudyRestClient;
    }

    @Override
    public CourseDtoResponse create(CreateCourseDto courseDto) {

        if (courseRepository.existsByTitle(courseDto.getTitle().toLowerCase())){
            throw new CourseAlreadyExistException("This course already exist");
        }

        // Trying to get microservice users to find teacher
        Teacher teacher;

        try {
            teacher = this.userRestClient.findOneTeacherById(courseDto.getIdUser());
        } catch (Exception e) {
            LOGGER.error("And error occurred during finding user {}", e.getMessage(), e);
            throw new RuntimeException("Teacher not found");
        }

        GraduateLevel graduateLevel;
        try {
            graduateLevel = this.fieldStudyRestClient.findOneGraduate(courseDto.getIdLevel());
        } catch (Exception e) {
            LOGGER.error("And error occurred during finding field study {}", e.getMessage(), e);

            throw new RuntimeException("Graduate level not found");
        }

        Course course = this.courseMapper.fromCourseDto(courseDto);
        course.setTitle(courseDto.getTitle().toLowerCase());
        course.setTeacher(teacher);
        course.setGraduateLevel(graduateLevel);

        Course saveCourse = this.courseRepository.save(course);

        return this.courseMapper.fromCourse(saveCourse);
    }

    @Override
    public CourseDtoResponse update(Long idCourse, UpdateCourseDto courseDto) {
        Course course = this.courseRepository.findById(idCourse).orElseThrow(() -> new CourseNotFoundException("Course not found"));

        if (courseDto.getTitle() == null || courseDto.getTitle().isEmpty()) {
            courseDto.setTitle(course.getTitle());
        }

        if (courseDto.getDescription() == null || courseDto.getDescription().isEmpty()) {
            courseDto.setDescription(course.getDescription());
        }

        if (courseDto.getCreditNumber() == 0) {
            courseDto.setCreditNumber(course.getCreditNumber());
        }

        if (courseDto.getTotalHours() == 0){
            courseDto.setTotalHours(course.getTotalHours());
        }

        // Trying to get microservice users to find teacher
        Teacher teacher;

        try {
            teacher = this.userRestClient.findOneTeacherById(courseDto.getIdUser());
        } catch (Exception e) {
            LOGGER.error("And error occurred during finding user {}", e.getMessage(), e);
            throw new RuntimeException("Teacher not found");
        }

        GraduateLevel graduateLevel;
        try {
            graduateLevel = this.fieldStudyRestClient.findOneGraduate(courseDto.getIdLevel());
        } catch (Exception e) {
            LOGGER.error("And error occurred during finding field study {}", e.getMessage(), e);

            throw new RuntimeException("Graduate level not found");
        }

        Course courseDtoUpdate = this.courseMapper.fromCourseDtoUpdate(courseDto);
        courseDtoUpdate.setIdCourse(idCourse);
        courseDtoUpdate.setGraduateLevel(graduateLevel);
        courseDtoUpdate.setTeacher(teacher);

        Course updateCourse = this.courseRepository.save(courseDtoUpdate);

        return this.courseMapper.fromCourse(updateCourse);
    }

    @Override
    public CourseDtoResponse findOneById(Long idCourse) {
        Course course = this.courseRepository.findById(idCourse).orElseThrow(() -> new CourseNotFoundException("Course not found"));

        // Trying to get microservice users to find teacher
        Teacher teacher;

        try {
            teacher = this.userRestClient.findOneTeacherById(course.getIdUser());
        } catch (Exception e) {
            LOGGER.error("And error occurred during finding user {}", e.getMessage(), e);
            throw new RuntimeException("Teacher not found");
        }

        GraduateLevel graduateLevel;
        try {
            graduateLevel = this.fieldStudyRestClient.findOneGraduate(course.getIdLevel());
        } catch (Exception e) {
            LOGGER.error("And error occurred during finding field study {}", e.getMessage(), e);

            throw new RuntimeException("Graduate level not found");
        }

        course.setTeacher(teacher);
        course.setGraduateLevel(graduateLevel);

        return this.courseMapper.fromCourse(course);
    }

    @Override
    public List<CourseDtoWithoutTeacher> findAllByIdUser(Long idUser) {
        List<Course> courseList = this.courseRepository.findAllByIdUser(idUser);

        PaginateResponse<GraduateLevel> allGraduate = this.fieldStudyRestClient.findAllGraduate(0, 10);
        List<GraduateLevel> graduateLevels = allGraduate.getData();

        Map<String, GraduateLevel> graduateLevelMap = new HashMap<>();
        for (GraduateLevel graduateLevel : graduateLevels) {
            graduateLevelMap.put(graduateLevel.getIdLevel(), graduateLevel);
        }

        List<CourseDtoWithoutTeacher> courses = new ArrayList<>();
        for (Course course : courseList) {
            GraduateLevel graduateLevel = graduateLevelMap.get(course.getIdLevel());
            if (graduateLevel != null) {
                CourseDtoWithoutTeacher dtoWithoutTeacher = this.courseMapper.fromCourseWithoutTeacher(course);
                dtoWithoutTeacher.setGraduateLevel(graduateLevel);
                courses.add(dtoWithoutTeacher);
            }
        }

        return courses;
    }


    @Override
    public Course findCourseById(Long idCourse) {
        return this.courseRepository.findById(idCourse).get();
    }

    @Override
    public PaginateResponse<CourseDtoResponse> findAll(PaginateDTO paginateDTO) {

        PageRequest pageRequest = PageRequest.of(paginateDTO.getOffset(), paginateDTO.getLimit(), Sort.by("title").ascending());
        Page<Course> courses = this.courseRepository.findAll(pageRequest);

        PaginateResponse<GraduateLevel> allGraduate = this.fieldStudyRestClient.findAllGraduate(paginateDTO.getOffset(), paginateDTO.getLimit());
        List<GraduateLevel> graduateLevels = allGraduate.getData();

        PaginateResponse<Teacher> allTeacher = this.userRestClient.findAllTeacher(paginateDTO.getOffset(), paginateDTO.getLimit());
        List<Teacher> teacherList = allTeacher.getData();

        List<CourseDtoResponse> dtoResponses = new ArrayList<>();

        for (Course course : courses) {
            // Find course corresponding to graduate level
            Optional<GraduateLevel> optionalGraduateLevel = graduateLevels.stream()
                    .filter(gl -> gl.getIdLevel().equals(course.getIdLevel()))
                    .findFirst();

            Optional<Teacher> optionalTeacher = teacherList.stream()
                    .filter(tc -> tc.getIdUser().equals(course.getIdUser()))
                    .findFirst();

            if (optionalGraduateLevel.isPresent()) {

                GraduateLevel graduateLevel = optionalGraduateLevel.get();

                if (optionalTeacher.isPresent()){

                    Teacher teacher = optionalTeacher.get();

                    CourseDtoResponse courseDtoResponse = courseMapper.fromCourse(course);
                    courseDtoResponse.setGraduateLevel(graduateLevel);
                    courseDtoResponse.setTeacher(teacher);
                    dtoResponses.add(courseDtoResponse);
                }
            }
        }

        long totalCount = courses.getTotalElements();
        long totalPage = courses.getTotalPages();
        long currentPage = courses.getNumber();
        boolean hasMore = paginateDTO.getOffset() + paginateDTO.getLimit() < totalCount;
        return new PaginateResponse<>(totalCount, hasMore, dtoResponses, currentPage, totalPage);
    }

    @Override
    public CourseDtoResponse delete(Long idCourse) {
        Course course = this.courseRepository.findById(idCourse).orElseThrow(() -> new CourseNotFoundException("Course not found"));

        this.courseRepository.deleteById(idCourse);

        return this.courseMapper.fromCourse(course);
    }
}
