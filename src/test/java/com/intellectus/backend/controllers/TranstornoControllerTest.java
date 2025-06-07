package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.TranstornosDTO;
import com.intellectus.backend.servicios.TranstornoService;
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

@WebMvcTest(TranstornoController.class)
class TranstornoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TranstornoService transtornoService;

    @Test
    void obtenerTranstornos_exito() throws Exception {
        // Simula que el servicio retorna una lista con un elemento
        Mockito.when(transtornoService.obtenerTranstornos())
                .thenReturn(List.of(new TranstornosDTO(1, "Trastorno de ejemplo")));

        mockMvc.perform(get("/transtornos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Trastorno de ejemplo"));
    }

    @Test
    void obtenerTranstornos_error() throws Exception {
        // Simula que el servicio lanza una excepción
        Mockito.when(transtornoService.obtenerTranstornos())
                .thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/transtornos"))
                .andExpect(status().is5xxServerError());
    }
}