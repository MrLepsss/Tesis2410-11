package com.intellectus.backend.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.intellectus.backend.dto.ConsultaLoteDTO;
import com.intellectus.backend.dto.SeleccionTrastornoDTO;
import com.intellectus.backend.dto.TranstornoEspecificoDTO;
import com.intellectus.backend.dto.TranstornosDTO;
import com.intellectus.backend.servicios.TranstornoService;

@RestController
public class TranstornoController {

    private TranstornoService transtornoService;

    public TranstornoController(TranstornoService transtornoService) {
        this.transtornoService = transtornoService;
    }

    @GetMapping("/transtornos")
    public List<TranstornosDTO> obtenerTranstornos() {
        return transtornoService.obtenerTranstornos();
    }

    @GetMapping("/transtornoGeneral/{transtornoId}")
    public List<TranstornosDTO> obtenerTranstornosGeneral(@PathVariable Integer transtornoId) {
        return transtornoService.obtenerTranstornosGeneral(transtornoId);
    }

    @GetMapping("/transtornoEspecifico/{transtornoGeneralId}")
    public List<TranstornoEspecificoDTO> obtenerTranstornosEspecificos(@PathVariable Integer transtornoGeneralId) {
        return transtornoService.obtenerTranstornosEspecificos(transtornoGeneralId);
    }

    @PostMapping("/sincronizarTranstornoEspecificoConsulta")
    public ResponseEntity<String> sincronizarTranstornoEspecificoConsulta(@RequestBody ConsultaLoteDTO dto) {
        return transtornoService.sincronizarTranstornoEspecificoConsulta(dto);
    }

    @GetMapping("/seleccionTrastorno/{consultaId}")
    public List<SeleccionTrastornoDTO> obtenerSeleccionTranstorno(@PathVariable Integer consultaId) {
        return transtornoService.obtenerSeleccionTranstorno(consultaId);
    }
}