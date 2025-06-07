package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.ConsultaDTO;
import com.intellectus.backend.dto.FechaConsultaDTO;
import com.intellectus.backend.dto.IdMessageDTO;
import com.intellectus.backend.servicios.ConsultaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultaController.class)
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConsultaService consultaService;

    @Test
    void crearConsultaPorPacienteId_exito() throws Exception {
        IdMessageDTO respuesta = new IdMessageDTO(1, "Consulta creada con éxito");
        Mockito.when(consultaService.crearConsulta(1))
                .thenReturn(ResponseEntity.status(201).body(respuesta));

        mockMvc.perform(post("/consulta/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Consulta creada con éxito"));
    }

    @Test
    void crearConsultaPorPacienteId_error() throws Exception {
        Mockito.when(consultaService.crearConsulta(1))
                .thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(post("/consulta/1"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void buscarConsultas_exito() throws Exception {
        List<ConsultaDTO> lista = List.of(new ConsultaDTO(1, LocalDate.of(2024, 1, 1)));
        Mockito.when(consultaService.buscarConsultas(1)).thenReturn(lista);

        mockMvc.perform(get("/consulta/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fecha").value("2024-01-01"));
    }

    @Test
    void buscarConsultas_error() throws Exception {
        Mockito.when(consultaService.buscarConsultas(1))
                .thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/consulta/1"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void crearConsultaPorFecha_exito() throws Exception {
        IdMessageDTO respuesta = new IdMessageDTO(2, "Consulta creada con éxito");
        Mockito.when(consultaService.crearConsultaFecha(any(FechaConsultaDTO.class)))
                .thenReturn(ResponseEntity.status(201).body(respuesta));

        String json = """
            {
                "id": 1,
                "fechaConsulta": "2024-05-19"
            }
            """;

        mockMvc.perform(post("/consulta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.mensaje").value("Consulta creada con éxito"));
    }

    @Test
    void crearConsultaPorFecha_error() throws Exception {
        Mockito.when(consultaService.crearConsultaFecha(any(FechaConsultaDTO.class)))
                .thenThrow(new RuntimeException("Error interno"));

        String json = """
            {
                "id": 1,
                "fechaConsulta": "2024-05-19"
            }
            """;

        mockMvc.perform(post("/consulta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }
}
