package com.ds3128.fieldstudy.modules.fieldstudy;

import com.ds3128.fieldstudy.modules.fieldstudy.entities.FieldStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldStudyRepository extends JpaRepository<FieldStudy, String> {
    boolean existsByCode(String code);

    boolean existsByName(String name);

    FieldStudy deleteByIdField(String idField);
}
