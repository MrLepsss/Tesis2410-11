package com.intellectus.backend.repositorios;

import com.intellectus.backend.entities.GraficaFormula;
import com.intellectus.backend.entities.RespuestaGrafico;
import com.intellectus.backend.entities.id.RespuestaGraficoId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaGraficoRepository extends JpaRepository<RespuestaGrafico, RespuestaGraficoId> {

    RespuestaGrafico findByConsultaIdAndParteGraficoId(int consulta, int id);

    List<GraficaFormula> findByConsultaId(Integer consultaId);
}
