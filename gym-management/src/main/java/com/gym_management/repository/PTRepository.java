package com.gym_management.repository;

import com.gym_management.model.PT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PTRepository extends JpaRepository<PT, Long> {
    PT findByPtId(Long ptId);
}
