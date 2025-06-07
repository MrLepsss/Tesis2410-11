package com.intellectus.backend.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.intellectus.backend.entities.TranstornoGeneral;

@Repository
public interface TranstornoGeneralRepository extends JpaRepository<TranstornoGeneral, Integer> {

    List<TranstornoGeneral> findByTipoDeTranstornoId(Integer transtornoId);
}