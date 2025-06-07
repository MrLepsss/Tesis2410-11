package com.intellectus.backend.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intellectus.backend.entities.Paciente;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    List<Paciente> findAllByCedula(String cedula);
}