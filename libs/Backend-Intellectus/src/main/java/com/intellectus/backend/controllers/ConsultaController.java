package com.intellectus.backend.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.intellectus.backend.dto.ConsultaDTO;
import com.intellectus.backend.dto.FechaConsultaDTO;
import com.intellectus.backend.dto.IdMessageDTO;
import com.intellectus.backend.servicios.ConsultaService;

@RestController
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping("/consulta/{pacienteId}")
    public ResponseEntity<IdMessageDTO> crearConsulta(@PathVariable Integer pacienteId) {
        return consultaService.crearConsulta(pacienteId);
    }
    @GetMapping("/consulta/{pacienteId}")
    public List<ConsultaDTO> buscarConsultas(@PathVariable Integer pacienteId)
    {
        return consultaService.buscarConsultas(pacienteId);
    }
    @PostMapping("/consulta")
    public ResponseEntity<IdMessageDTO> crearConsulta(@RequestBody FechaConsultaDTO fechaConsultaDTO) {
        return consultaService.crearConsultaFecha(fechaConsultaDTO);
    }
}