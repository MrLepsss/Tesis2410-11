package com.intellectus.backend.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.intellectus.backend.entities.ArchivoConsulta;

@Repository
public interface ArchivoConsultaRepository extends JpaRepository<ArchivoConsulta, Integer> {
    Optional<ArchivoConsulta> findByRutaArchivo(String rutaArchivo);
    List<ArchivoConsulta> findAllByConsulta_Paciente_Id(Long pacienteId);
    List<ArchivoConsulta> findAllByConsulta_Paciente_Cedula(String cedula);
}
