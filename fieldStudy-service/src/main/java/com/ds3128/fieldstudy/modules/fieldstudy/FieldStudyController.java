package com.ds3128.fieldstudy.modules.fieldstudy;

import com.ds3128.fieldstudy.common.dtos.PaginateDTO;
import com.ds3128.fieldstudy.common.types.PaginateResponse;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.CreateFieldStudyDto;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.FieldStudyDtoResponse;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.UpdateFieldStudyDto;
import com.ds3128.fieldstudy.modules.fieldstudy.services.FieldStudyService;
import com.ds3128.fieldstudy.modules.graduate.dtos.CreateGraduateLevelDto;
import com.ds3128.fieldstudy.modules.graduate.entities.GraduateLevel;
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

@RequestMapping("/fieldStudy")
@Tag(name = "Field Study Controller")
@RequiredArgsConstructor
@RestController
public class FieldStudyController {

    private final FieldStudyService fieldStudyService;

    @Operation(summary = "Create Field study with object")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Field study created",
                            content = {@Content(mediaType = "application/json",schema = @Schema(implementation = CreateGraduateLevelDto.class))}),
                    @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content)}
    )
    @PostMapping("/create")
    public ResponseEntity<FieldStudyDtoResponse> create(@Validated @RequestBody CreateFieldStudyDto createFieldStudyDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(this.fieldStudyService.create(createFieldStudyDto));
    }

    @Operation(summary = "Find all field study")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fund field study level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = GraduateLevel.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content)})
    @GetMapping("/findAll")
    public ResponseEntity<PaginateResponse<FieldStudyDtoResponse>> findAll(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PaginateDTO paginateDTO = PaginateDTO.builder()
                .limit(limit)
                .offset(offset)
                .build();

        return ResponseEntity.ok(this.fieldStudyService.findAll(paginateDTO));
    }

    @Operation(summary = "Find a field study by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fund field study level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = GraduateLevel.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Field study not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid field study id",content = @Content)})
    @GetMapping("/findById/{idField}")
    public ResponseEntity<FieldStudyDtoResponse> findOneById(@PathVariable String idField) {

        return ResponseEntity.ok(this.fieldStudyService.findOneById(idField));
    }

    @Operation(summary = "Update a field study by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated field study level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = GraduateLevel.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Field study not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid field study id",content = @Content)})
    @PatchMapping("/update/{idField}")
    public ResponseEntity<FieldStudyDtoResponse> update(@PathVariable String idField, @RequestBody UpdateFieldStudyDto updateFieldStudyDto) {

        return ResponseEntity.status(HttpStatus.OK).body(this.fieldStudyService.update(idField, updateFieldStudyDto));
    }

    @Operation(summary = "Delete a field study by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted field study level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = GraduateLevel.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Field study not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid field study id",content = @Content)})
    @DeleteMapping("/delete/{idLevel}")
    public ResponseEntity<FieldStudyDtoResponse> delete(@PathVariable  String idLevel){
        return ResponseEntity.ok(this.fieldStudyService.delete(idLevel));
    }
}
