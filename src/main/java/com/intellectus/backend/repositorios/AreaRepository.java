package com.intellectus.backend.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.intellectus.backend.entities.Area;

public interface AreaRepository extends JpaRepository<Area, Integer> {
}