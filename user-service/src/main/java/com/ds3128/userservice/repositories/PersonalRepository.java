package com.ds3128.userservice.repositories;

import com.ds3128.userservice.entities.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
    Optional<Personal> findByEmail(String email);
}
