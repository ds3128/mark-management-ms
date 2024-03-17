package com.ds3128.fieldstudy.modules.graduate;

import com.ds3128.fieldstudy.modules.graduate.entities.GraduateLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraduateLevelRepository extends JpaRepository<GraduateLevel, String> {

    boolean existsByCode(String code);

    boolean existsByName(String name);

    GraduateLevel deleteByIdLevel(String id);
}
