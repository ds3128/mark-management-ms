package com.ds3128.courseservice.modules.notes.services;

import com.ds3128.courseservice.common.dtos.PaginateDTO;
import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.assignment.AssignmentMapperImpl;
import com.ds3128.courseservice.modules.assignment.dtos.AssignmentDtoResponse;
import com.ds3128.courseservice.modules.assignment.entities.Assignment;
import com.ds3128.courseservice.modules.assignment.services.AssignmentService;
import com.ds3128.courseservice.modules.client.UserRestClient;
import com.ds3128.courseservice.modules.models.Student;
import com.ds3128.courseservice.modules.notes.MarkMapperImpl;
import com.ds3128.courseservice.modules.notes.MarkRepository;
import com.ds3128.courseservice.modules.notes.dtos.CreateMarkDto;
import com.ds3128.courseservice.modules.notes.dtos.MarkDtoResponse;
import com.ds3128.courseservice.modules.notes.dtos.UpdateMarkDto;
import com.ds3128.courseservice.modules.notes.entities.Mark;
import com.ds3128.courseservice.modules.notes.exceptions.MarkNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    private final MarkMapperImpl markMapper;

    private final AssignmentService assignmentService;

    private final AssignmentMapperImpl assignmentMapper;

    private final UserRestClient userRestClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkServiceImpl.class);

    public MarkServiceImpl(MarkRepository markRepository, MarkMapperImpl markMapper, AssignmentService assignmentService, AssignmentMapperImpl assignmentMapper, UserRestClient userRestClient) {
        this.markRepository = markRepository;
        this.markMapper = markMapper;
        this.assignmentService = assignmentService;
        this.assignmentMapper = assignmentMapper;
        this.userRestClient = userRestClient;
    }

    @Override
    public MarkDtoResponse create(CreateMarkDto createMarkDto) {
        try {
            Student student = this.userRestClient.findOneStudentById(createMarkDto.getIdUser());

            AssignmentDtoResponse assignmentDtoResponse = this.assignmentService.findOneById(createMarkDto.getIdAssignment());

            Assignment assignment = this.assignmentMapper.fromAssignmentDtoResponse(assignmentDtoResponse);

            Mark mark = this.markMapper.fromMarkDtoCreate(createMarkDto);
            mark.setIdMark(null);
            mark.setIdUser(createMarkDto.getIdUser());
            mark.setStudent(student);
            mark.setAssignment(assignment);

            Mark save = this.markRepository.save(mark);

            return this.markMapper.fromMark(save);
        } catch (Exception e) {
            LOGGER.error("An error during creating mark : {}", e.getMessage(), e);
            throw new RuntimeException("Student not found", e);
        }
    }

    @Override
    public MarkDtoResponse update(Long idMark, UpdateMarkDto updateMarkDto) {
        Mark markExist = this.markRepository.findById(idMark).orElseThrow(() -> new MarkNotFoundException("Mark not found"));

        if (updateMarkDto.getObservation() == null || updateMarkDto.getObservation().isEmpty()) {
            updateMarkDto.setObservation(markExist.getObservation());
        }

        Student student;

        try {
            student = this.userRestClient.findOneStudentById(markExist.getIdUser());
        } catch (Exception e) {
            LOGGER.error("An error during creating mark : {}", e.getMessage(), e);
            throw new RuntimeException("Student not found", e);
        }

        Mark mark = this.markMapper.fromMarkDtoUpdate(updateMarkDto);
        mark.setAssignment(markExist.getAssignment());
        mark.setStudent(student);
        mark.setIdUser(student.getIdUser());
        mark.setIdMark(idMark);

        Mark save = this.markRepository.save(mark);

        return this.markMapper.fromMark(save);
    }

    @Override
    public MarkDtoResponse findOneById(Long idMark) {
        Mark markExist = this.markRepository.findById(idMark).orElseThrow(() -> new MarkNotFoundException("Mark not found"));

        Student student;

        try {
            student = this.userRestClient.findOneStudentById(markExist.getIdUser());
        } catch (Exception e) {
            LOGGER.error("An error during creating mark : {}", e.getMessage(), e);
            throw new RuntimeException("Student not found", e);
        }

        markExist.setStudent(student);

        return this.markMapper.fromMark(markExist);
    }

    @Override
    public Mark findMarkById(Long idMark) {
        return this.markRepository.findById(idMark).get();
    }

    @Override
    public List<Mark> findAllByIdUser(Long idUser) {
        return this.markRepository.findAllByIdUser(idUser);
    }

    @Override
    public PaginateResponse<MarkDtoResponse> findAll(PaginateDTO paginateDTO) {
        PageRequest pageRequest = PageRequest.of(paginateDTO.getOffset(), paginateDTO.getLimit(), Sort.by("value").ascending());
        Page<Mark> markPage = this.markRepository.findAll(pageRequest);

        PaginateResponse<Student> studentPage = this.userRestClient.findAllStudent(paginateDTO.getOffset(), paginateDTO.getLimit());
        List<Student> students = studentPage.getData();

        List<MarkDtoResponse> markDtoResponseList = new ArrayList<>();

        for (Mark mark : markPage) {
            // Find user(student) corresponding to mark
            Optional<Student> optionalStudent = students.stream()
                    .filter(student -> student.getIdUser().equals(mark.getIdUser()))
                    .findFirst();
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();
                MarkDtoResponse markDtoResponse = markMapper.fromMark(mark);
                markDtoResponse.setStudent(student);
                markDtoResponseList.add(markDtoResponse);
            }
        }

        long totalPage = markPage.getTotalPages();
        long totalCount = markPage.getTotalElements();
        long currentPage = markPage.getNumber();
        boolean hasMore = paginateDTO.getOffset() + paginateDTO.getLimit() < totalCount;
        return new PaginateResponse<>(totalCount, hasMore, markDtoResponseList, currentPage, totalPage);
    }



    @Override
    public MarkDtoResponse delete(Long idMark) {
        Mark markExist = this.markRepository.findById(idMark).orElseThrow(() -> new MarkNotFoundException("Mark not found"));

        this.markRepository.delete(markExist);

        return this.markMapper.fromMark(markExist);
    }
}
