package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.RespuestaGraficoDTO;
import com.intellectus.backend.servicios.RespuestaGraficoService;
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

@WebMvcTest(RespuestaGraficoController.class)
class RespuestaGraficoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RespuestaGraficoService respuestaGraficoService;

    @Test
    void guardarGrafica_exito() throws Exception {
        Mockito.when(respuestaGraficoService.guardarRespuestaGrafico(Mockito.any(RespuestaGraficoDTO.class)))
                .thenReturn(ResponseEntity.ok("Respuesta Grafica guardada con éxito."));

        String json = """
            {
                "idConsulta": 1,
                "idParteGrafico": 2,
                "valor": "valor"
            }
            """;

        mockMvc.perform(post("/guardarGrafica")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Respuesta Grafica guardada con éxito."));
    }

    @Test
    void guardarGrafica_error() throws Exception {
        Mockito.when(respuestaGraficoService.guardarRespuestaGrafico(Mockito.any(RespuestaGraficoDTO.class)))
                .thenThrow(new RuntimeException("Error interno"));

        String json = """
            {
                "idConsulta": 1,
                "idParteGrafico": 2,
                "valor": "valor"
            }
            """;

        mockMvc.perform(post("/guardarGrafica")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }
}
