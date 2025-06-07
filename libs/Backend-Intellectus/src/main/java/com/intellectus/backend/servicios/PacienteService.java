package com.intellectus.backend.servicios;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.intellectus.backend.dto.IdMessageDTO;
import com.intellectus.backend.dto.PacienteDTO;
import com.intellectus.backend.dto.PacienteListadoDTO;
import com.intellectus.backend.entities.Paciente;
import com.intellectus.backend.repositorios.PacienteRepository;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public ResponseEntity<IdMessageDTO> crearPaciente(PacienteDTO dto) {
        if (!pacienteRepository.findAllByCedula(dto.getCedula()).isEmpty()) {
            IdMessageDTO respuesta = new IdMessageDTO(
                    null,
                    ("Ya existe un paciente con la cédula: " + dto.getCedula()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);

        }
        Paciente paciente = new Paciente(dto.getNombre(), dto.getCedula(), dto.getFechaNacimiento(),
                dto.getLateralidad());
        IdMessageDTO respuesta = new IdMessageDTO(
                pacienteRepository.save(paciente).getId(),
                ("Paciente creado de manera exitosa: "));
        return ResponseEntity.ok().body(respuesta);
    }

    public List<PacienteListadoDTO> getAllPacientes() {
        return pacienteRepository.findAll()
                .stream()
                .map(paciente -> new PacienteListadoDTO(paciente.getId(), paciente.getNombre(), paciente.getCedula()))
                .toList();
    }

    public ResponseEntity<IdMessageDTO> getPacienteByCedula(String cedula) {
        List<Paciente> pacientes = pacienteRepository.findAllByCedula(cedula);
        if (pacientes.isEmpty()) {
            IdMessageDTO respuesta = new IdMessageDTO(
                    null,
                    ("No existe un paciente con la cédula: " + cedula));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
        Paciente paciente = pacientes.get(0);
        IdMessageDTO respuesta = new IdMessageDTO(
                paciente.getId(),
                ("Paciente encontrado: " + paciente.getNombre()));
        return ResponseEntity.ok().body(respuesta);
    }
}