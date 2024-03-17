package com.ds3128.courseservice.modules.notes;

import com.ds3128.courseservice.modules.notes.entities.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findAllByIdUser(Long idUser);
}
