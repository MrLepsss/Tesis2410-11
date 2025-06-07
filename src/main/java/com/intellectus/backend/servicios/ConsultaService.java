package com.intellectus.backend.servicios;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.intellectus.backend.dto.ConsultaDTO;
import com.intellectus.backend.dto.FechaConsultaDTO;
import com.intellectus.backend.dto.IdMessageDTO;
import com.intellectus.backend.entities.Consulta;
import com.intellectus.backend.entities.Paciente;
import com.intellectus.backend.repositorios.ConsultaRepository;
import com.intellectus.backend.repositorios.PacienteRepository;

@Service
public class ConsultaService {
    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public ResponseEntity<IdMessageDTO> crearConsulta(Integer idPaciente) {
        Optional<Paciente> paciente = pacienteRepository.findById(idPaciente);

        if (paciente.isEmpty()) {
            IdMessageDTO errorResponse = new IdMessageDTO(
                    null,
                    "Paciente no encontrado con ID: " + idPaciente);
            return ResponseEntity.status(404).body(errorResponse);
        }

        LocalDate fechaEvaluacion = LocalDate.now();
        Consulta consulta = new Consulta(paciente.get(), fechaEvaluacion,
                Period.between(paciente.get().getFechaNacimiento(), fechaEvaluacion).getYears());

        Consulta consultaGuardada = consultaRepository.save(consulta);

        IdMessageDTO respuesta = new IdMessageDTO(
                consultaGuardada.getId(),
                "Consulta creada con éxito");

        return ResponseEntity.status(201).body(respuesta);
    }

    public List<ConsultaDTO> buscarConsultas(Integer pacienteId) {
        List<ConsultaDTO> consultaDTO = new ArrayList<>();
        List<Consulta> consultas = consultaRepository.findByPacienteId(pacienteId);
        for (Consulta consulta : consultas) {
            consultaDTO.add(new ConsultaDTO(
                    consulta.getId(),
                    consulta.getFechaEvaluacion()));
        }
        return consultaDTO;
    }

    public ResponseEntity<IdMessageDTO> crearConsultaFecha(FechaConsultaDTO fechaConsultaDTO) {
        Optional<Paciente> paciente = pacienteRepository.findById(fechaConsultaDTO.getId());

        if (paciente.isEmpty()) {
            IdMessageDTO errorResponse = new IdMessageDTO(
                    null,
                    "Paciente no encontrado con ID: " + fechaConsultaDTO.getId());
            return ResponseEntity.status(404).body(errorResponse);
        }

        LocalDate fechaEvaluacion = LocalDate.parse(fechaConsultaDTO.getFechaConsulta());
        Consulta consulta = new Consulta(paciente.get(), fechaEvaluacion,
                Period.between(paciente.get().getFechaNacimiento(), fechaEvaluacion).getYears());

        Consulta consultaGuardada = consultaRepository.save(consulta);

        IdMessageDTO respuesta = new IdMessageDTO(
                consultaGuardada.getId(),
                "Consulta creada con éxito");

        return ResponseEntity.status(201).body(respuesta);
    }
}