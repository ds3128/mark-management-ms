package com.ds3128.fieldstudy.modules.graduate.services;

import com.ds3128.fieldstudy.common.dtos.PaginateDTO;
import com.ds3128.fieldstudy.common.types.PaginateResponse;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.FieldStudyDtoResponse;
import com.ds3128.fieldstudy.modules.fieldstudy.services.FieldStudyService;
import com.ds3128.fieldstudy.modules.fieldstudy.entities.FieldStudy;
import com.ds3128.fieldstudy.modules.fieldstudy.FieldStudyMapperImpl;
import com.ds3128.fieldstudy.modules.graduate.dtos.*;
import com.ds3128.fieldstudy.modules.graduate.entities.GraduateLevel;
import com.ds3128.fieldstudy.modules.graduate.exceptions.GraduateLevelAlreadyExistException;
import com.ds3128.fieldstudy.modules.graduate.exceptions.GraduateLevelNotFoundException;
import com.ds3128.fieldstudy.modules.graduate.GraduateLevelMapperImpl;
import com.ds3128.fieldstudy.modules.graduate.GraduateLevelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GraduateLevelServiceImpl implements GraduateLevelService {

    private final GraduateLevelRepository graduateLevelRepository;

    private final FieldStudyService fieldStudyService;

    private final GraduateLevelMapperImpl graduateLevelMapper;

    private final FieldStudyMapperImpl fieldStudyMapper;

    @Override
    public GraduateLevelDtoResponse create(CreateGraduateLevelDto createGraduateLevelDto) {

        if (createGraduateLevelDto.getCode().isBlank() || createGraduateLevelDto.getCode().isEmpty()) {
            throw new IllegalArgumentException("Code level can't be null");
        }

        if (createGraduateLevelDto.getName().isBlank() || createGraduateLevelDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name level can't be null");
        }

        if (this.graduateLevelRepository.existsByCode(createGraduateLevelDto.getCode())){
            throw new GraduateLevelAlreadyExistException("Level code already exist");
        }

        if (graduateLevelRepository.existsByName(createGraduateLevelDto.getName())) {
            throw new GraduateLevelAlreadyExistException("Level name already exist");
        }

        log.info("creating graduate level with name {}", createGraduateLevelDto.getName());

        // Get field of study
        FieldStudyDtoResponse fieldStudyDtoResponse = this.fieldStudyService.findOneById(createGraduateLevelDto.getIdFieldStudy());
        FieldStudy fieldStudy = this.fieldStudyMapper.fromFieldStudyResponse(fieldStudyDtoResponse);

        // Create level and associate it with his study field
        GraduateLevel graduateLevel = this.graduateLevelMapper.fromGraduateLevelDto(createGraduateLevelDto);
        graduateLevel.setFieldStudy(fieldStudy);
        graduateLevel.setName(createGraduateLevelDto.getName().toLowerCase());
        graduateLevel.setCode(createGraduateLevelDto.getCode().toLowerCase());

        // Save graduate level in database
        GraduateLevel saveGraduateLevel = this.graduateLevelRepository.save(graduateLevel);
        log.info("graduate level {} created ", createGraduateLevelDto.getName());

        // Return graduate level
        return this.graduateLevelMapper.fromGraduateLevel(saveGraduateLevel);
    }

    @Override
    public GraduateLevelDtoResponse update(String idLevel, UpdateGraduateLevelDto graduateLevelDtoRequest) {

        GraduateLevel graduateLevelExist = this.graduateLevelRepository.findById(idLevel).orElseThrow(() -> new GraduateLevelNotFoundException("Graduate level not found"));

        if (graduateLevelDtoRequest.getCode() == null || graduateLevelDtoRequest.getCode().isEmpty()) {
            graduateLevelDtoRequest.setCode(graduateLevelExist.getCode());
        }

        if (graduateLevelDtoRequest.getName() == null || graduateLevelDtoRequest.getName().isEmpty()) {
            graduateLevelDtoRequest.setName(graduateLevelExist.getName());
        }

        GraduateLevel graduateLevel = this.graduateLevelMapper.fromGraduateLevelDtoUpdate(graduateLevelDtoRequest);
        graduateLevel.setFieldStudy(graduateLevelExist.getFieldStudy());
        graduateLevel.setIdLevel(idLevel);

        GraduateLevel saveGraduate = this.graduateLevelRepository.save(graduateLevel);

        return this.graduateLevelMapper.fromGraduateLevel(saveGraduate);
    }

    @Override
    public GraduateLevelDtoResponse delete(String idLevel) {

        this.graduateLevelRepository.findById(idLevel).orElseThrow(() -> new GraduateLevelNotFoundException("Graduate level not found"));

        GraduateLevel graduateLevel = this.graduateLevelRepository.deleteByIdLevel(idLevel);

        return this.graduateLevelMapper.fromGraduateLevel(graduateLevel);
    }

    @Override
    public GraduateLevelDtoResponse findOneById(String idLevel) {
        GraduateLevel graduateLevel = this.graduateLevelRepository.findById(idLevel).orElseThrow(() -> new GraduateLevelNotFoundException("Graduate level not found"));

        return this.graduateLevelMapper.fromGraduateLevel(graduateLevel);
    }

    @Override
    public GraduateLevel findOneGraduate(String idLevel) {
        return this.graduateLevelRepository.findById(idLevel).get();
    }

    @Override
    public PaginateResponse<GraduateLevelDtoResponse> findAll(PaginateDTO paginateDTO) {

        PageRequest pageRequest = PageRequest.of(paginateDTO.getOffset(), paginateDTO.getLimit(), Sort.by("name").ascending());
        Page<GraduateLevel> graduateLevelPage = this.graduateLevelRepository.findAll(pageRequest);

        List<GraduateLevelDtoResponse> graduateLevelDtoResponseList = graduateLevelPage.stream().map(graduateLevelMapper::fromGraduateLevel).toList();

        long totalCount = graduateLevelPage.getTotalElements();
        long totalPages = graduateLevelPage.getTotalPages();
        long currentPage = graduateLevelPage.getNumber();
        boolean hasMore = paginateDTO.getOffset() + paginateDTO.getLimit() < totalCount;
        return new  PaginateResponse<>(graduateLevelPage.getTotalElements(), hasMore, graduateLevelDtoResponseList, currentPage, totalPages);
    }
}
