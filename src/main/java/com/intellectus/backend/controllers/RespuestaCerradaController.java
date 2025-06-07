package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.RespuestaCerradaDTO;
import com.intellectus.backend.servicios.RespuestaCerradaService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RespuestaCerradaController {

    private final RespuestaCerradaService respuestaCerradaService;

    public RespuestaCerradaController(RespuestaCerradaService respuestaCerradaService) {
        this.respuestaCerradaService = respuestaCerradaService;
    }

    @PostMapping("/guardarCerradas")
    public ResponseEntity<String> guardarRespuesta(@RequestBody List<RespuestaCerradaDTO> respuestas) {
        return respuestaCerradaService.guardarRespuestasCerradas(respuestas);
    }

    @PostMapping("/guardarCerrada")
    public ResponseEntity<String> guardarRespuestaCerradaUnica(@RequestBody RespuestaCerradaDTO respuesta) {
        return respuestaCerradaService.guardarRespuestaCerrada(respuesta);
    }
}