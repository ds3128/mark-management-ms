package com.ds3128.userservice.controller;

import com.ds3128.userservice.common.dtos.PaginateDTO;
import com.ds3128.userservice.common.types.PaginateResponse;
import com.ds3128.userservice.dtos.StudentDtoRequest;
import com.ds3128.userservice.dtos.TeacherDtoRequest;
import com.ds3128.userservice.entities.Student;
import com.ds3128.userservice.entities.Teacher;
import com.ds3128.userservice.services.PersonalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/")
@RestController
public class PersonalController {

    private final PersonalService personalService;

    @Operation(summary = "Create student")
    @PostMapping("/createStudent")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@Validated @RequestBody StudentDtoRequest studentDtoRequest) {
        return this.personalService.createStudent(studentDtoRequest);
    }

    @Operation(summary = "Create teacher")
    @PostMapping("/createTeacher")
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher createTeacher(@Validated @RequestBody TeacherDtoRequest teacherDtoRequest) {
        return this.personalService.createTeacher(teacherDtoRequest);
    }

    @Operation(summary = "Update student")
    @PatchMapping("/updateStudent/{idUser}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Student updateStudent(@PathVariable Long idUser, @RequestBody StudentDtoRequest studentDtoRequest) {
        return this.personalService.updateStudent(idUser, studentDtoRequest);
    }

    @Operation(summary = "Update teacher")
    @PatchMapping("/updateTeacher/{idUser}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Teacher updateTeacher(@PathVariable Long idUser, @RequestBody TeacherDtoRequest teacherDtoRequest) {
        return this.personalService.updateTeacher(idUser, teacherDtoRequest);
    }

    @Operation(summary = "Find student by his id")
    @GetMapping("/findStudent/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public Student findOneStudentById(@PathVariable Long idUser) {
        return this.personalService.findOneStudentById(idUser);
    }

    @Operation(summary = "Find student by his id(is also used by another microservices)")
    @GetMapping("/findStudentById/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public Student findOneStudent(@PathVariable Long idUser) {
        return this.personalService.findOneStudent(idUser);
    }

    @Operation(summary = "Find student by his id(is also used by another microservices)")
    @GetMapping("/findTeacherById/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public Teacher findOneTeacher(@PathVariable Long idUser) {
        return this.personalService.findOneTeacher(idUser);
    }

    @Operation(summary = "Find teacher by his id")
    @GetMapping("/findTeacher/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public Teacher findOneTeacherById(@PathVariable Long idUser) {
        return this.personalService.findOneTeacherById(idUser);
    }

    @Operation(summary = "Delete student by his id")
    @DeleteMapping("/deleteStudent/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public Student deleteStudentById(@PathVariable Long idUser) {
        return this.personalService.deleteStudentById(idUser);
    }

    @Operation(summary = "Delete teacher by his id")
    @DeleteMapping("/deleteTeacher/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public Teacher deleteTeacherById(@PathVariable Long idUser) {
        return this.personalService.deleteTeacherById(idUser);
    }

    @Operation(summary = "Find all student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fund field study level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content)})
    @GetMapping("/findAllStudent")
    public PaginateResponse<Student> findAllStudent(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PaginateDTO paginateDTO = PaginateDTO.builder()
                .limit(limit)
                .offset(offset)
                .build();

        return this.personalService.findAllStudent(paginateDTO);
    }

    @Operation(summary = "Find all field study")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fund field study level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Teacher.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content)})
    @GetMapping("/findAllTeacher")
    public PaginateResponse<Teacher> findAllTeacher(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PaginateDTO paginateDTO = PaginateDTO.builder()
                .limit(limit)
                .offset(offset)
                .build();

        return this.personalService.findAllTeacher(paginateDTO);
    }
}
