package com.ds3128.fieldstudy.modules.fieldstudy.services;

import com.ds3128.fieldstudy.common.types.PaginateResponse;
import com.ds3128.fieldstudy.common.dtos.PaginateDTO;
import com.ds3128.fieldstudy.modules.fieldstudy.FieldStudyMapperImpl;
import com.ds3128.fieldstudy.modules.fieldstudy.FieldStudyRepository;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.CreateFieldStudyDto;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.FieldStudyDtoResponse;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.UpdateFieldStudyDto;
import com.ds3128.fieldstudy.modules.fieldstudy.entities.FieldStudy;
import com.ds3128.fieldstudy.modules.fieldstudy.exceptions.FieldStudyAlreadyExistException;
import com.ds3128.fieldstudy.modules.fieldstudy.exceptions.FieldStudyNotFoundException;
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
public class FieldStudyServiceImpl implements FieldStudyService {

    private final FieldStudyRepository repository;

    private final FieldStudyMapperImpl fieldStudyMapper;

    @Override
    public FieldStudyDtoResponse create(CreateFieldStudyDto createFieldStudyDto) {

        if (createFieldStudyDto.getName().isBlank() || createFieldStudyDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name field can't be null");
        }

        if (createFieldStudyDto.getCode().isBlank() || createFieldStudyDto.getCode().isEmpty()) {
            throw new IllegalArgumentException("Code field can't be null");
        }

        if (this.repository.existsByCode(createFieldStudyDto.getCode())) {
            throw new FieldStudyAlreadyExistException("This field code already exist");
        }

        if (this.repository.existsByName(createFieldStudyDto.getName())) {
            throw new FieldStudyAlreadyExistException("This field name already exist");
        }

        // Convert dto field study to entity
        FieldStudy fieldStudy = this.fieldStudyMapper.fromFieldStudyDtoCreate(createFieldStudyDto);

        // Save field study to database
        FieldStudy saveFieldStudy = this.repository.save(fieldStudy);

        // Convert field study saved and return it using field study response
        return this.fieldStudyMapper.fromFieldStudy(saveFieldStudy);
    }

    @Override
    public FieldStudyDtoResponse update(String idField, UpdateFieldStudyDto updateFieldStudyDto) {

        FieldStudy fieldStudyExist = this.repository.findById(idField).orElseThrow(() -> new FieldStudyNotFoundException("field id : {} not found" + idField));

        if (updateFieldStudyDto.getName() == null || updateFieldStudyDto.getName().isEmpty()) {
            updateFieldStudyDto.setName(fieldStudyExist.getName());
        }

        if (updateFieldStudyDto.getCode() == null || updateFieldStudyDto.getCode().isEmpty()) {
            updateFieldStudyDto.setCode(fieldStudyExist.getCode());
        }

        // Convert dto field study to entity
        FieldStudy updateFieldStudy = this.fieldStudyMapper.fromFieldStudyDtoUpdate(updateFieldStudyDto);
        updateFieldStudy.setIdField(idField);

        // Save field study to database
        FieldStudy saved = this.repository.save(updateFieldStudy);

        // Convert field study saved and return it using field study response
        return this.fieldStudyMapper.fromFieldStudy(saved);
    }

    @Override
    public FieldStudyDtoResponse delete(String idField) {

        FieldStudy fieldExist = this.repository.findById(idField).orElseThrow(() -> new FieldStudyNotFoundException("field study not found"));

        FieldStudy fieldStudy = this.repository.deleteByIdField(fieldExist.getIdField());

        return this.fieldStudyMapper.fromFieldStudy(fieldStudy);
    }

    @Override
    public FieldStudyDtoResponse findOneById(String idField) {

        FieldStudy fieldExist = this.repository.findById(idField).orElseThrow(() -> new FieldStudyNotFoundException("field study not found"));

        return this.fieldStudyMapper.fromFieldStudy(fieldExist);
    }

    @Override
    public PaginateResponse<FieldStudyDtoResponse> findAll(PaginateDTO paginateDTO) {
        PageRequest pageRequest = PageRequest.of(paginateDTO.getOffset(), paginateDTO.getLimit(), Sort.by("name").ascending());
        Page<FieldStudy> fieldStudyPage = this.repository.findAll(pageRequest);

        List<FieldStudyDtoResponse> fieldStudyDtoResponseList = fieldStudyPage.stream().map(fieldStudyMapper::fromFieldStudy).toList();

        long totalElements = fieldStudyPage.getTotalElements();
        long totalPages = fieldStudyPage.getTotalPages();
        long currentPage = fieldStudyPage.getNumber();
        boolean hasMore = paginateDTO.getOffset() + paginateDTO.getLimit() < totalElements;
        return new  PaginateResponse<>(totalElements, hasMore, fieldStudyDtoResponseList, currentPage, totalPages);
    }
}
