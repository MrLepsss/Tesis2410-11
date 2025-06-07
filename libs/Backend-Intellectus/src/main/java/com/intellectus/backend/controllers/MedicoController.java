package com.intellectus.backend.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.intellectus.backend.dto.ConsultaLoteDTO;
import com.intellectus.backend.dto.MedicoDTO;
import com.intellectus.backend.servicios.MedicoService;

@RestController
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping("/medicos")
    public List<MedicoDTO> obtenerTodos() {
        return medicoService.obtenerTodos();
    }

    @PostMapping("/sincronizarMedicosConsulta")
    public ResponseEntity<String> sincronizarMedicosConsulta(@RequestBody ConsultaLoteDTO dto) {
        return medicoService.sincronizarMedicosConsulta(dto);
    }

    @GetMapping("/medicos/{consultaId}")
    public List<MedicoDTO> obtenerMedicosConsulta(@PathVariable Integer consultaId)
    {
        return medicoService.obtenerMedicoConsulta(consultaId);
    }
}