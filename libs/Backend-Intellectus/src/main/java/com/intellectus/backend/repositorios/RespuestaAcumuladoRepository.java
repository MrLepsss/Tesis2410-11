package com.intellectus.backend.repositorios;

import com.intellectus.backend.entities.RespuestaAcumulado;
import com.intellectus.backend.entities.id.AcumuladoCategoriaId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaAcumuladoRepository extends JpaRepository<RespuestaAcumulado, AcumuladoCategoriaId> {

    List<RespuestaAcumulado> findByConsultaId(Integer consultaId);

    RespuestaAcumulado findByConsultaIdAndCategoriaId(Integer consultaId, Integer id);

}
