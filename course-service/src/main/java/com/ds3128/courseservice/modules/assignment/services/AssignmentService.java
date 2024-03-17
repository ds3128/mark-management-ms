package com.ds3128.courseservice.modules.assignment.services;

import com.ds3128.courseservice.common.dtos.PaginateDTO;
import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.assignment.dtos.AssignmentDtoResponse;
import com.ds3128.courseservice.modules.assignment.dtos.CreateAssignmentDto;
import com.ds3128.courseservice.modules.assignment.dtos.UpdateAssignmentDto;

public interface AssignmentService {
    AssignmentDtoResponse create(CreateAssignmentDto assignmentDto);

    AssignmentDtoResponse update(Long idAssignment, UpdateAssignmentDto assignmentDto);

    AssignmentDtoResponse findOneById(Long idAssignment);

    AssignmentDtoResponse delete(Long idAssignment);

    PaginateResponse<AssignmentDtoResponse> findAll(PaginateDTO paginateDTO);

}
