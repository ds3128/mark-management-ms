package com.ds3128.courseservice.modules.assignment.services;

import com.ds3128.courseservice.common.dtos.PaginateDTO;
import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.assignment.AssignmentMapperImpl;
import com.ds3128.courseservice.modules.assignment.AssignmentRepository;
import com.ds3128.courseservice.modules.assignment.dtos.AssignmentDtoResponse;
import com.ds3128.courseservice.modules.assignment.dtos.CreateAssignmentDto;
import com.ds3128.courseservice.modules.assignment.dtos.UpdateAssignmentDto;
import com.ds3128.courseservice.modules.assignment.entities.Assignment;
import com.ds3128.courseservice.modules.assignment.exceptions.AssignmentAlreadyExistException;
import com.ds3128.courseservice.modules.course.CourseMapperImpl;
import com.ds3128.courseservice.modules.course.dtos.CourseDtoResponse;
import com.ds3128.courseservice.modules.course.entities.Course;
import com.ds3128.courseservice.modules.course.exceptions.CourseNotFoundException;
import com.ds3128.courseservice.modules.course.services.CourseService;
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
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;

    private final AssignmentMapperImpl assignmentMapper;

    private final CourseService courseService;

    private final CourseMapperImpl courseMapper;
    @Override
    public AssignmentDtoResponse create(CreateAssignmentDto createAssignmentDto) {
        if (createAssignmentDto.getTitle().isBlank() || createAssignmentDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title can't be empty");
        }

        if (createAssignmentDto.getWeight().isBlank() || createAssignmentDto.getWeight().isEmpty()) {
            throw new IllegalArgumentException("Weight can't be null");
        }

        if (this.assignmentRepository.existsByTitle(createAssignmentDto.getTitle().toLowerCase())) {
            throw new AssignmentAlreadyExistException("Assignment title already exists");
        }

        log.info("Initial creating assignment {}", createAssignmentDto.getTitle());
        // Find course that we want to link at assignment
        CourseDtoResponse courseDtoResponse = this.courseService.findOneById(createAssignmentDto.getIdCourse());
        Course course = this.courseMapper.fromCourseDtoResponse(courseDtoResponse);

        Assignment assignment = this.assignmentMapper.fromAssignmentDtoCreate(createAssignmentDto);
        assignment.setCourse(course);
        assignment.setIdAssignment(null);
        assignment.setTitle(createAssignmentDto.getTitle().toLowerCase());

        Assignment savedAssignment = this.assignmentRepository.save(assignment);

        log.info("Assignment {} created", savedAssignment.getTitle());

        return this.assignmentMapper.fromAssignment(savedAssignment);
    }

    @Override
    public AssignmentDtoResponse update(Long idAssignment, UpdateAssignmentDto assignmentDto) {

        Assignment assignment = this.assignmentRepository.findById(idAssignment).orElseThrow(() -> new CourseNotFoundException("Course not found"));

        if (assignmentDto.getTitle() == null || assignmentDto.getTitle().isEmpty()) {
            assignmentDto.setTitle(assignment.getTitle());
        }
        if (assignmentDto.getDescription() == null || assignmentDto.getDescription().isEmpty()) {
            assignmentDto.setDescription(assignment.getDescription());
        }
        if (assignmentDto.getDocument() == null || assignmentDto.getDocument().isEmpty()) {
            assignmentDto.setDocument(assignment.getDocument());
        }
        if (assignmentDto.getWeight() == null || assignmentDto.getWeight().isEmpty()) {
            assignmentDto.setWeight(assignment.getWeight());
        }

        Assignment assignmentDtoUpdate = this.assignmentMapper.fromAssignmentDtoUpdate(assignmentDto);

        assignmentDtoUpdate.setCourse(assignment.getCourse());
        assignmentDtoUpdate.setIdAssignment(idAssignment);

        Assignment save = this.assignmentRepository.save(assignmentDtoUpdate);

        return this.assignmentMapper.fromAssignment(save);
    }

    @Override
    public AssignmentDtoResponse findOneById(Long idAssignment) {

        Assignment assignment = this.assignmentRepository.findById(idAssignment).orElseThrow(() -> new CourseNotFoundException("Course not found"));

        return this.assignmentMapper.fromAssignment(assignment);
    }

    @Override
    public AssignmentDtoResponse delete(Long idAssignment) {
        Assignment assignment = this.assignmentRepository.findById(idAssignment).orElseThrow(() -> new CourseNotFoundException("Course not found"));

        this.assignmentRepository.deleteById(idAssignment);

        return this.assignmentMapper.fromAssignment(assignment);
    }

    @Override
    public PaginateResponse<AssignmentDtoResponse> findAll(PaginateDTO paginateDTO) {

        PageRequest pageRequest = PageRequest.of(paginateDTO.getOffset(), paginateDTO.getLimit(), Sort.by("title").ascending());
        Page<Assignment> assignmentPage = this.assignmentRepository.findAll(pageRequest);

        List<AssignmentDtoResponse> assignmentDtoResponseList = assignmentPage.stream().map(assignmentMapper::fromAssignment).toList();

        long totalCount = assignmentPage.getTotalElements();
        long totalPages = assignmentPage.getTotalPages();
        long currentPage = assignmentPage.getNumber();
        boolean hasMore = paginateDTO.getOffset() + paginateDTO.getLimit() < totalCount;
        return new PaginateResponse<>(totalCount, hasMore, assignmentDtoResponseList, currentPage, totalPages);
    }
}
