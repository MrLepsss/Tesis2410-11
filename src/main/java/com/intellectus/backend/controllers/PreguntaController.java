package com.intellectus.backend.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.intellectus.backend.dto.PreguntaDTO;
import com.intellectus.backend.servicios.PreguntaService;

@RestController
public class PreguntaController {

    private final PreguntaService preguntaService;

    public PreguntaController(PreguntaService preguntaService) {
        this.preguntaService = preguntaService;
    }

    @GetMapping("/pregunta/{categoriaId}/{consultaId}")
    public List<PreguntaDTO> getCategoriasByAreaId(@PathVariable Integer categoriaId,
            @PathVariable Integer consultaId) {
        return preguntaService.obtenerPreguntasPorCategoria(categoriaId, consultaId);
    }
}