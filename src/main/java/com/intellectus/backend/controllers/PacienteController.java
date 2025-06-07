package com.intellectus.backend.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.intellectus.backend.dto.IdMessageDTO;
import com.intellectus.backend.dto.PacienteDTO;
import com.intellectus.backend.dto.PacienteListadoDTO;
import com.intellectus.backend.servicios.PacienteService;

@RestController
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/CrearPaciente")
    public ResponseEntity<IdMessageDTO> crearPaciente(@RequestBody PacienteDTO dto) {
        return pacienteService.crearPaciente(dto);
    }

    @GetMapping("/pacientes")
    public List<PacienteListadoDTO> getAllPacientes() {
        return pacienteService.getAllPacientes();
    }

    @GetMapping("/pacientes/{cedula}")
    public ResponseEntity<IdMessageDTO> getPacienteByCedula(@PathVariable String cedula) {
        return pacienteService.getPacienteByCedula(cedula);
    }
}

