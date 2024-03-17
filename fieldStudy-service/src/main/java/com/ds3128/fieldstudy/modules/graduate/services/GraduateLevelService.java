package com.ds3128.fieldstudy.modules.graduate.services;

import com.ds3128.fieldstudy.common.types.PaginateResponse;
import com.ds3128.fieldstudy.common.dtos.PaginateDTO;
import com.ds3128.fieldstudy.modules.graduate.dtos.CreateGraduateLevelDto;
import com.ds3128.fieldstudy.modules.graduate.dtos.UpdateGraduateLevelDto;
import com.ds3128.fieldstudy.modules.graduate.dtos.GraduateLevelDtoResponse;
import com.ds3128.fieldstudy.modules.graduate.entities.GraduateLevel;

public interface GraduateLevelService {

    GraduateLevelDtoResponse create(CreateGraduateLevelDto createGraduateLevelDto);

    GraduateLevelDtoResponse update(String idLevel, UpdateGraduateLevelDto graduateLevelDtoRequest);

    GraduateLevelDtoResponse delete(String idLevel);

    GraduateLevelDtoResponse findOneById(String idLevel);

    GraduateLevel findOneGraduate(String idLevel);

    PaginateResponse<GraduateLevelDtoResponse> findAll(PaginateDTO paginateDTO);
}
