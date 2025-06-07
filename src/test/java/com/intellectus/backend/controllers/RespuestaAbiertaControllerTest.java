package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.RespuestaAbiertaDTO;
import com.intellectus.backend.servicios.RespuestaAbiertaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(RespuestaAbiertaController.class)
class RespuestaAbiertaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RespuestaAbiertaService respuestaAbiertaService;

    @Test
    void guardarRespuestasAbiertas_exito() throws Exception {
        Mockito.when(respuestaAbiertaService.guardarRespuestasAbiertas(Mockito.anyList()))
                .thenReturn(ResponseEntity.ok("Respuestas abiertas guardadas con éxito."));

        String json = """
            [
                {"valor":"texto","idConsulta":1,"idPregunta":2}
            ]
            """;

        mockMvc.perform(post("/guardarAbiertas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Respuestas abiertas guardadas con éxito."));
    }

    @Test
    void guardarRespuestasAbiertas_error() throws Exception {
        Mockito.when(respuestaAbiertaService.guardarRespuestasAbiertas(Mockito.anyList()))
                .thenThrow(new RuntimeException("Error interno"));

        String json = """
            [
                {"valor":"texto","idConsulta":1,"idPregunta":2}
            ]
            """;

        mockMvc.perform(post("/guardarAbiertas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void guardarRespuestaAbiertaUnica_exito() throws Exception {
        Mockito.when(respuestaAbiertaService.guardarRespuestaAbierta(Mockito.any(RespuestaAbiertaDTO.class)))
                .thenReturn(ResponseEntity.ok("Respuesta abierta guardada con éxito."));

        String json = """
            {"valor":"texto","idConsulta":1,"idPregunta":2}
            """;

        mockMvc.perform(post("/guardarAbierta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Respuesta abierta guardada con éxito."));
    }

    @Test
    void guardarRespuestaAbiertaUnica_error() throws Exception {
        Mockito.when(respuestaAbiertaService.guardarRespuestaAbierta(Mockito.any(RespuestaAbiertaDTO.class)))
                .thenThrow(new RuntimeException("Error interno"));

        String json = """
            {"valor":"texto","idConsulta":1,"idPregunta":2}
            """;

        mockMvc.perform(post("/guardarAbierta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }
}
