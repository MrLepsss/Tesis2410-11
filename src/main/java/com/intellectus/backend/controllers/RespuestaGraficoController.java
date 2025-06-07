package com.intellectus.backend.controllers;
import com.intellectus.backend.dto.RespuestaGraficoDTO;
import com.intellectus.backend.servicios.RespuestaGraficoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RespuestaGraficoController {

    private final RespuestaGraficoService respuestaGraficoService;

    public RespuestaGraficoController(RespuestaGraficoService respuestaGraficoService) {
        this.respuestaGraficoService = respuestaGraficoService;
    }

    @PostMapping("/guardarGrafica")
    public ResponseEntity<String> guardarGrafica(@RequestBody RespuestaGraficoDTO respuesta) {
        return respuestaGraficoService.guardarRespuestaGrafico(respuesta);
    }

}