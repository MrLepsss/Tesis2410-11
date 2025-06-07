package com.intellectus.backend.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.intellectus.backend.entities.ConsultaMedico;
import com.intellectus.backend.entities.id.ConsultaMedicoId;

@Repository
public interface ConsultaMedicoRepository extends JpaRepository<ConsultaMedico, ConsultaMedicoId> {
     ConsultaMedico findByConsultaIdAndMedicoId(Integer idConsulta, Integer idMedico);

     List<ConsultaMedico> findByConsultaId(Integer idConsulta);
}