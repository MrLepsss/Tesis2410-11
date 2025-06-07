package com.intellectus.backend.repositorios;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intellectus.backend.entities.RespuestaCerrada;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaCerradaRepository extends JpaRepository<RespuestaCerrada, Integer> {
    RespuestaCerrada findByConsultaIdAndPreguntaId(Integer idConsulta, Integer idPregunta);

    List<RespuestaCerrada> findByConsultaIdInAndPreguntaIdIn(Set<Integer> consultaIds, Set<Integer> preguntaIds);

    List<RespuestaCerrada> findByConsultaId(Integer consultaId);
}