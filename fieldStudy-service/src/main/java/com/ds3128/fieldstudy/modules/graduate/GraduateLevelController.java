package com.ds3128.fieldstudy.modules.graduate;

import com.ds3128.fieldstudy.common.dtos.PaginateDTO;
import com.ds3128.fieldstudy.common.types.PaginateResponse;
import com.ds3128.fieldstudy.modules.graduate.dtos.CreateGraduateLevelDto;
import com.ds3128.fieldstudy.modules.graduate.dtos.UpdateGraduateLevelDto;
import com.ds3128.fieldstudy.modules.graduate.dtos.GraduateLevelDtoResponse;
import com.ds3128.fieldstudy.modules.graduate.entities.GraduateLevel;
import com.ds3128.fieldstudy.modules.graduate.services.GraduateLevelService;
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

@RequestMapping("/graduate")
@Tag(name = "Graduate Level Controller")
@RequiredArgsConstructor
@RestController
public class GraduateLevelController {

    private final GraduateLevelService graduateLevelService;

    @Operation(summary = "Create graduate with object")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Graduate level created",
                            content = {@Content(mediaType = "application/json",schema = @Schema(implementation = CreateGraduateLevelDto.class))}),
                    @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content)}
    )
    @PostMapping("/create")
    public ResponseEntity<GraduateLevelDtoResponse> create(@Validated @RequestBody CreateGraduateLevelDto createGraduateLevelDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(this.graduateLevelService.create(createGraduateLevelDto));
    }

    @Operation(summary = "Find all graduate level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fund graduate level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = GraduateLevel.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content)})
    @GetMapping("/findAll")
    public ResponseEntity<PaginateResponse<GraduateLevelDtoResponse>> findAll(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PaginateDTO paginateDTO = PaginateDTO.builder()
                .limit(limit)
                .offset(offset)
                .build();

        return ResponseEntity.ok(this.graduateLevelService.findAll(paginateDTO));
    }

    @Operation(summary = "Find graduate level by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fund graduate level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = GraduateLevel.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Graduate level not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid graduate level id",content = @Content)})
    @GetMapping("/findById/{idLevel}")
    public ResponseEntity<GraduateLevelDtoResponse> findOneById(@PathVariable String idLevel) {

        return ResponseEntity.ok(this.graduateLevelService.findOneById(idLevel));
    }

    @Operation(summary = "Find graduate level by its id(Do not use inside this microservice because it's use by another)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fund graduate level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = GraduateLevel.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Graduate level not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid graduate level id",content = @Content)})
    @GetMapping("/findOneGraduateById/{idLevel}")
    public ResponseEntity<GraduateLevel> findOneGraduate(@PathVariable String idLevel) {
        return ResponseEntity.ok(this.graduateLevelService.findOneGraduate(idLevel));
    }

    @Operation(summary = "Update graduate level by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated graduate level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = GraduateLevel.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Graduate level not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid graduate level id",content = @Content)})
    @PatchMapping("/update/{idLevel}")
    public ResponseEntity<GraduateLevelDtoResponse> update(@PathVariable String idLevel, @RequestBody UpdateGraduateLevelDto updateGraduateLevelDto) {

        return ResponseEntity.ok(this.graduateLevelService.update(idLevel, updateGraduateLevelDto));
    }

    @Operation(summary = "Delete a graduate by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted graduate level",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = GraduateLevel.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid student id",content = @Content)})
    @DeleteMapping("/delete/{idLevel}")
    public ResponseEntity<GraduateLevelDtoResponse> delete(@PathVariable  String idLevel){
        return ResponseEntity.ok(this.graduateLevelService.delete(idLevel));
    }
}
