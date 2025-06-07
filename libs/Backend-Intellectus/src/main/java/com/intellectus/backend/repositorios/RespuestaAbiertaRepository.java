package com.intellectus.backend.repositorios;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intellectus.backend.entities.RespuestaAbierta;
import org.springframework.stereotype.Repository;

@Repository

public interface RespuestaAbiertaRepository extends JpaRepository<RespuestaAbierta, Integer> {
    RespuestaAbierta findByConsultaIdAndPreguntaId(Integer idConsulta, Integer idPregunta);

    List<RespuestaAbierta> findByConsultaIdInAndPreguntaIdIn(Set<Integer> consultaIds, Set<Integer> preguntaIds);

    List<RespuestaAbierta> findByConsultaId(Integer consultaId);
}