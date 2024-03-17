package com.ds3128.courseservice.modules.notes;

import com.ds3128.courseservice.common.dtos.PaginateDTO;
import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.assignment.dtos.CreateAssignmentDto;
import com.ds3128.courseservice.modules.assignment.dtos.UpdateAssignmentDto;
import com.ds3128.courseservice.modules.course.entities.Course;
import com.ds3128.courseservice.modules.notes.dtos.CreateMarkDto;
import com.ds3128.courseservice.modules.notes.dtos.MarkDtoResponse;
import com.ds3128.courseservice.modules.notes.dtos.UpdateMarkDto;
import com.ds3128.courseservice.modules.notes.entities.Mark;
import com.ds3128.courseservice.modules.notes.services.MarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/mark")
@Tag(name = "Mark Controller")
@RestController
public class MarkController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @Operation(summary = "Create mark using object")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Mark created",
                            content = {@Content(mediaType = "application/json",schema = @Schema(implementation = CreateAssignmentDto.class))}),
                    @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content)}
    )
    @PostMapping("/create")
    public ResponseEntity<MarkDtoResponse> create(@Validated @RequestBody CreateMarkDto createMarkDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.markService.create(createMarkDto));
    }

    @Operation(summary = "Update mark by his id and using object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated mark",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = UpdateAssignmentDto.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Mark not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid mark id",content = @Content)})
    @PatchMapping("/update/{idMark}")
    public ResponseEntity<MarkDtoResponse> update(@PathVariable Long idMark, @RequestBody UpdateMarkDto updateMarkDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.markService.update(idMark, updateMarkDto));
    }

    @Operation(summary = "Find mark by his id")
    @GetMapping("/findOne/{idMark}")
    @ResponseStatus(HttpStatus.OK)
    public MarkDtoResponse findOneById(@PathVariable Long idMark){
        return this.markService.findOneById(idMark);
    }

    @Operation(summary = "This method is used by another app to find mark")
    @GetMapping("/findMarkById/{idMark}")
    @ResponseStatus(HttpStatus.OK)
    public Mark findMarkById(@PathVariable Long idMark) {
        return this.markService.findMarkById(idMark);
    }

    @Operation(summary = "Find all mark")
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public PaginateResponse<MarkDtoResponse> findAll(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit){
        PaginateDTO paginateDTO = PaginateDTO.builder()
                .limit(limit)
                .offset(offset)
                .build();

        return this.markService.findAll(paginateDTO);
    }

    @Operation(summary = "This end point is used by other microservices to get all marks student using his idUser")
    @GetMapping("/findAllMarkById/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public List<Mark> findAllByIdUser(@PathVariable Long idUser) {
        return this.markService.findAllByIdUser(idUser);
    }

    @Operation(summary = "Delete mark by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted mark",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Course.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Mark not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid mark id",content = @Content)})
    @DeleteMapping("/delete/{idMark}")
    public MarkDtoResponse delete(@PathVariable  Long idMark){

        return this.markService.delete(idMark);
    }

}
