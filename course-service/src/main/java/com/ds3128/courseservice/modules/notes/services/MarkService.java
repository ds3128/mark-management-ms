package com.ds3128.courseservice.modules.notes.services;

import com.ds3128.courseservice.common.dtos.PaginateDTO;
import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.notes.dtos.CreateMarkDto;
import com.ds3128.courseservice.modules.notes.dtos.MarkDtoResponse;
import com.ds3128.courseservice.modules.notes.dtos.UpdateMarkDto;
import com.ds3128.courseservice.modules.notes.entities.Mark;

import java.util.List;

public interface MarkService {

    MarkDtoResponse create(CreateMarkDto createMarkDto);

    MarkDtoResponse update(Long idMark, UpdateMarkDto updateMarkDto);

    MarkDtoResponse findOneById(Long idMark);

    Mark findMarkById(Long idMark);

    List<Mark> findAllByIdUser(Long idUser);

    PaginateResponse<MarkDtoResponse> findAll(PaginateDTO paginateDTO);

    MarkDtoResponse delete(Long idMark);
}
