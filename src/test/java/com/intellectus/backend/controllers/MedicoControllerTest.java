package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.ConsultaLoteDTO;
import com.intellectus.backend.dto.MedicoDTO;
import com.intellectus.backend.servicios.MedicoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicoController.class)
class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicoService medicoService;

    @Test
    void obtenerTodos_exito() throws Exception {
        List<MedicoDTO> lista = List.of(new MedicoDTO(1, "Dr. Juan", "Psiquiatría"));
        Mockito.when(medicoService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/medicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Dr. Juan"))
                .andExpect(jsonPath("$[0].especialidad").value("Psiquiatría"));
    }

    @Test
    void obtenerTodos_error() throws Exception {
        Mockito.when(medicoService.obtenerTodos()).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/medicos"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void sincronizarMedicosConsulta_exito() throws Exception {
        Mockito.when(medicoService.sincronizarMedicosConsulta(any(ConsultaLoteDTO.class)))
                .thenReturn(ResponseEntity.ok("Relación médico-consulta sincronizada con éxito."));

        String json = """
            {
                "consultaId": 1,
                "ids": [1,2,3]
            }
            """;

        mockMvc.perform(post("/sincronizarMedicosConsulta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Relación médico-consulta sincronizada con éxito."));
    }

    @Test
    void sincronizarMedicosConsulta_error() throws Exception {
        Mockito.when(medicoService.sincronizarMedicosConsulta(any(ConsultaLoteDTO.class)))
                .thenThrow(new RuntimeException("Error interno"));

        String json = """
            {
                "consultaId": 1,
                "ids": [1,2,3]
            }
            """;

        mockMvc.perform(post("/sincronizarMedicosConsulta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void obtenerMedicosConsulta_exito() throws Exception {
        List<MedicoDTO> lista = List.of(new MedicoDTO(2, "Dra. Ana", "Neurología"));
        Mockito.when(medicoService.obtenerMedicoConsulta(1)).thenReturn(lista);

        mockMvc.perform(get("/medicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Dra. Ana"))
                .andExpect(jsonPath("$[0].especialidad").value("Neurología"));
    }

    @Test
    void obtenerMedicosConsulta_error() throws Exception {
        Mockito.when(medicoService.obtenerMedicoConsulta(1)).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/medicos/1"))
                .andExpect(status().is5xxServerError());
    }
}
