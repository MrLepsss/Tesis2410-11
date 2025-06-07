package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.RespuestaCerradaDTO;
import com.intellectus.backend.servicios.RespuestaCerradaService;
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

@WebMvcTest(RespuestaCerradaController.class)
class RespuestaCerradaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RespuestaCerradaService respuestaCerradaService;

    @Test
    void guardarRespuestasCerradas_exito() throws Exception {
        Mockito.when(respuestaCerradaService.guardarRespuestasCerradas(Mockito.anyList()))
                .thenReturn(ResponseEntity.ok("Respuestas cerradas guardadas con éxito."));

        String json = """
            [
                {"idConsulta":1,"idPregunta":2,"idOpcionListado":3}
            ]
            """;

        mockMvc.perform(post("/guardarCerradas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Respuestas cerradas guardadas con éxito."));
    }

    @Test
    void guardarRespuestasCerradas_error() throws Exception {
        Mockito.when(respuestaCerradaService.guardarRespuestasCerradas(Mockito.anyList()))
                .thenThrow(new RuntimeException("Error interno"));

        String json = """
            [
                {"idConsulta":1,"idPregunta":2,"idOpcionListado":3}
            ]
            """;

        mockMvc.perform(post("/guardarCerradas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void guardarRespuestaCerradaUnica_exito() throws Exception {
        Mockito.when(respuestaCerradaService.guardarRespuestaCerrada(Mockito.any(RespuestaCerradaDTO.class)))
                .thenReturn(ResponseEntity.ok("Respuesta cerrada guardada con éxito."));

        String json = """
            {"idConsulta":1,"idPregunta":2,"idOpcionListado":3}
            """;

        mockMvc.perform(post("/guardarCerrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Respuesta cerrada guardada con éxito."));
    }

    @Test
    void guardarRespuestaCerradaUnica_error() throws Exception {
        Mockito.when(respuestaCerradaService.guardarRespuestaCerrada(Mockito.any(RespuestaCerradaDTO.class)))
                .thenThrow(new RuntimeException("Error interno"));

        String json = """
            {"idConsulta":1,"idPregunta":2,"idOpcionListado":3}
            """;

        mockMvc.perform(post("/guardarCerrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }
}
