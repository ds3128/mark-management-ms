package com.ds3128.courseservice.modules.assignment;

import com.ds3128.courseservice.common.dtos.PaginateDTO;
import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.assignment.dtos.AssignmentDtoResponse;
import com.ds3128.courseservice.modules.assignment.dtos.CreateAssignmentDto;
import com.ds3128.courseservice.modules.assignment.dtos.UpdateAssignmentDto;
import com.ds3128.courseservice.modules.assignment.services.AssignmentService;
import com.ds3128.courseservice.modules.course.entities.Course;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/assignment")
@Tag(name = "Assignment Controller")
@RequiredArgsConstructor
@RestController
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Operation(summary = "Create assignment using object")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Assignment created",
                            content = {@Content(mediaType = "application/json",schema = @Schema(implementation = CreateAssignmentDto.class))}),
                    @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content)}
    )
    @PostMapping("/create")
    public ResponseEntity<AssignmentDtoResponse> create(@Validated @RequestBody CreateAssignmentDto createAssignmentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.assignmentService.create(createAssignmentDto));
    }

    @Operation(summary = "Update assignment by his id and using object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated assignment",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = UpdateAssignmentDto.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Assignment not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid assignment id",content = @Content)})
    @PatchMapping("/update/{idAssignment}")
    public ResponseEntity<AssignmentDtoResponse> update(@PathVariable Long idAssignment, @RequestBody UpdateAssignmentDto updateAssignmentDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.assignmentService.update(idAssignment, updateAssignmentDto));
    }

    @Operation(summary = "Find assignment by his id")
    @GetMapping("/findOne/{idAssignment}")
    @ResponseStatus(HttpStatus.OK)
    public AssignmentDtoResponse findOneById(@PathVariable Long idAssignment){
        return this.assignmentService.findOneById(idAssignment);
    }

    @Operation(summary = "Find all assignment")
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public PaginateResponse<AssignmentDtoResponse> findAll(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit){
        PaginateDTO paginateDTO = PaginateDTO.builder()
                .limit(limit)
                .offset(offset)
                .build();

        return this.assignmentService.findAll(paginateDTO);
    }

    @Operation(summary = "Delete assignment by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted assignment",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Course.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Assignment not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid assignment id",content = @Content)})
    @DeleteMapping("/delete/{idAssignment}")
    public AssignmentDtoResponse delete(@PathVariable  Long idAssignment){

        return this.assignmentService.delete(idAssignment);
    }
}
