package com.intellectus.backend.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.intellectus.backend.entities.TranstornoEspecifico;

@Repository
public interface TranstornoEspecificoRepository extends JpaRepository<TranstornoEspecifico, Integer> {

    List<TranstornoEspecifico> findByTranstornoGeneralId(Integer transtornoGeneralId);
}