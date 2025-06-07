package com.intellectus.backend.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.intellectus.backend.entities.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {
}