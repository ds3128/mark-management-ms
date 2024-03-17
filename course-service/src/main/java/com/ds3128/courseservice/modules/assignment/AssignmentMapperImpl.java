package com.ds3128.courseservice.modules.assignment;

import com.ds3128.courseservice.modules.assignment.dtos.AssignmentDtoResponse;
import com.ds3128.courseservice.modules.assignment.dtos.CreateAssignmentDto;
import com.ds3128.courseservice.modules.assignment.dtos.UpdateAssignmentDto;
import com.ds3128.courseservice.modules.assignment.entities.Assignment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AssignmentMapperImpl {
    private final ModelMapper modelMapper = new ModelMapper();

    public Assignment fromAssignmentDtoCreate(CreateAssignmentDto assignmentDto) {
        return this.modelMapper.map(assignmentDto, Assignment.class);
    }

    public Assignment fromAssignmentDtoUpdate(UpdateAssignmentDto assignmentDto) {
        return this.modelMapper.map(assignmentDto, Assignment.class);
    }

    public AssignmentDtoResponse fromAssignment(Assignment assignment) {
        return this.modelMapper.map(assignment, AssignmentDtoResponse.class);
    }

    public Assignment fromAssignmentDtoResponse(AssignmentDtoResponse assignmentDtoResponse){
        return this.modelMapper.map(assignmentDtoResponse, Assignment.class);
    }
}
