package com.intellectus.backend.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.intellectus.backend.entities.TranstornoEspecificoConsulta;
import com.intellectus.backend.entities.id.TranstornoEspecificoConsultaId;

@Repository
public interface TranstornoEspecificoConsultaRepository extends JpaRepository<TranstornoEspecificoConsulta, TranstornoEspecificoConsultaId> {

    List<TranstornoEspecificoConsulta> findByConsultaId(Integer idConsulta);
}