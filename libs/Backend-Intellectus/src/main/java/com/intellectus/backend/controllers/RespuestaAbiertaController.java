package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.RespuestaAbiertaDTO;
import com.intellectus.backend.servicios.RespuestaAbiertaService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RespuestaAbiertaController {

    private final RespuestaAbiertaService respuestaAbiertaService;

    public RespuestaAbiertaController(RespuestaAbiertaService respuestaAbiertaService) {
        this.respuestaAbiertaService = respuestaAbiertaService;
    }

    @PostMapping("/guardarAbiertas")
    public ResponseEntity<String> guardarRespuesta(@RequestBody List<RespuestaAbiertaDTO> respuestas) {
        return respuestaAbiertaService.guardarRespuestasAbiertas(respuestas);
    }

    @PostMapping("/guardarAbierta")
    public ResponseEntity<String> guardarRespuestaUnica(@RequestBody RespuestaAbiertaDTO respuesta) {
        return respuestaAbiertaService.guardarRespuestaAbierta(respuesta);
    }
}