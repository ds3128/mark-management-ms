package com.ds3128.courseservice.modules.client;

import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.models.Student;
import com.ds3128.courseservice.modules.models.Teacher;
import com.ds3128.courseservice.modules.models.enums.StudentStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@FeignClient(name = "USER-SERVICE")
public interface UserRestClient {


//    @CircuitBreaker(name = "courseService", fallbackMethod = "fallbackFindStudent")

//    @Bulkhead(name = "courseService", fallbackMethod = "fallbackFindStudent")
//    @TimeLimiter(name = "courseService", fallbackMethod = "fallbackFindStudent")
//    @Retry(name = "courseService", fallbackMethod = "fallbackFindStudent")
    @GetMapping("api/findStudentById/{idUser}")
    @Cacheable("studentByIdCache")
    Student findOneStudentById(@PathVariable  Long idUser);

    @GetMapping("api/findTeacherById/{idUser}")
    @Cacheable("teacherByIdCache")
    Teacher findOneTeacherById(@PathVariable  Long idUser);

    @GetMapping("api/findAllStudent")
    //    @CircuitBreaker(name = "courseService", fallbackMethod = "findAllStudent")
    @Cacheable("allStudentCache")
    PaginateResponse<Student> findAllStudent(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit);

    @GetMapping("api/findAllTeacher")
    @Cacheable("allTeacherCache")
    PaginateResponse<Teacher> findAllTeacher(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit);

    default Student fallbackFindStudent(Long idUser, Exception exception){
        Student student = new Student();
        student.setIdUser(idUser);
        student.setIdStudent("Not Available");
        student.setStudentStatus(StudentStatus.UNDERGRADUATE);
        student.setEmail("Not Available");
        student.setBirthday(LocalDate.of(2015, Month.JANUARY, 1));
        student.setFirstName("Not Available");
        student.setLastName("Not Available");
        student.setMarkList(null);
        student.setPhoneNumber("Not Available");

        return student;
    }

    default List<Student> findAllStudent(Exception exception) {
        return List.of();
    }
}
