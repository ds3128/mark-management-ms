package com.ds3128.fieldstudy.modules.fieldstudy.services;

import com.ds3128.fieldstudy.common.types.PaginateResponse;
import com.ds3128.fieldstudy.common.dtos.PaginateDTO;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.CreateFieldStudyDto;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.FieldStudyDtoResponse;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.UpdateFieldStudyDto;

public interface FieldStudyService {

    FieldStudyDtoResponse create(CreateFieldStudyDto createFieldStudyDto);

    FieldStudyDtoResponse update(String idField, UpdateFieldStudyDto updateFieldStudyDto);

    FieldStudyDtoResponse delete(String idField);

    FieldStudyDtoResponse findOneById(String idField);

    PaginateResponse<FieldStudyDtoResponse> findAll(PaginateDTO paginateDTO);
}
