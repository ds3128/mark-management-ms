package com.ds3128.courseservice.modules.course;

import com.ds3128.courseservice.common.dtos.PaginateDTO;
import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.course.dtos.CourseDtoResponse;
import com.ds3128.courseservice.modules.course.dtos.CourseDtoWithoutTeacher;
import com.ds3128.courseservice.modules.course.dtos.CreateCourseDto;
import com.ds3128.courseservice.modules.course.dtos.UpdateCourseDto;
import com.ds3128.courseservice.modules.course.entities.Course;
import com.ds3128.courseservice.modules.course.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/course")
@Tag(name = "Course Controller")
@RequiredArgsConstructor
@RestController
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Create course using object")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDtoResponse create(@Validated @RequestBody CreateCourseDto createCourseDto) {
        return this.courseService.create(createCourseDto);
    }

    @Operation(summary = "Update course by his id and using object")
    @PatchMapping("/update/{idCourse}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CourseDtoResponse update(@PathVariable Long idCourse, @RequestBody UpdateCourseDto updateCourseDto) {
        return this.courseService.update(idCourse, updateCourseDto);
    }

    @Operation(summary = "Find course by his id")
    @GetMapping("/findOne/{idCourse}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDtoResponse findOneById(@PathVariable Long idCourse){
        return this.courseService.findOneById(idCourse);
    }

    @Operation(summary = "This method is used by another app to find course")
    @GetMapping("/findCourseById/{idCourse}")
    @ResponseStatus(HttpStatus.OK)
    public Course findCourseById(@PathVariable Long idCourse) {
        return this.courseService.findCourseById(idCourse);
    }

    @Operation(summary = "Find all course")
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public PaginateResponse<CourseDtoResponse> findAll(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit){
        PaginateDTO paginateDTO = PaginateDTO.builder()
                .limit(limit)
                .offset(offset)
                .build();

        return this.courseService.findAll(paginateDTO);
    }

    @Operation(summary = "This end point is used by other microservices to get all teacher course using his idUser")
    @GetMapping("/findAllByIdUser/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDtoWithoutTeacher> findAllByIdUser(@PathVariable Long idUser) {
        return this.courseService.findAllByIdUser(idUser);
    }

    @Operation(summary = "Delete course by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted course",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Course.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Course not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid course id",content = @Content)})
    @DeleteMapping("/delete/{idCourse}")
    public CourseDtoResponse delete(@PathVariable  Long idCourse){

        return this.courseService.delete(idCourse);
    }

}
