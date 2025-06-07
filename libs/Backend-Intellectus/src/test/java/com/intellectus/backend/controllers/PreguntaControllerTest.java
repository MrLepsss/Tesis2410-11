package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.PreguntaDTO;
import com.intellectus.backend.servicios.PreguntaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PreguntaController.class)
class PreguntaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PreguntaService preguntaService;

    @Test
    void getCategoriasByAreaId_exito() throws Exception {
        Mockito.when(preguntaService.obtenerPreguntasPorCategoria(1, 1))
                .thenReturn(List.of(new PreguntaDTO(1, "¿Cómo te sientes?", "abierta", 2)));

        mockMvc.perform(get("/pregunta/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].textoPregunta").value("¿Cómo te sientes?"))
                .andExpect(jsonPath("$[0].tipoRespuesta").value("abierta"))
                .andExpect(jsonPath("$[0].idCategoria").value(2));
    }

    @Test
    void getCategoriasByAreaId_error() throws Exception {
        Mockito.when(preguntaService.obtenerPreguntasPorCategoria(1, 1))
                .thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/pregunta/1/1"))
                .andExpect(status().is5xxServerError());
    }
}
