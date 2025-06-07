package com.intellectus.backend.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intellectus.backend.entities.PreguntaReferencia;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaReferenciaRepository extends JpaRepository<PreguntaReferencia, Integer> {
    List<PreguntaReferencia> findByPreguntaIdIn(List<Integer> preguntaIds);
}