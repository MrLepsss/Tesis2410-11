package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.IdMessageDTO;
import com.intellectus.backend.dto.PacienteDTO;
import com.intellectus.backend.dto.PacienteListadoDTO;
import com.intellectus.backend.servicios.PacienteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PacienteController.class)
class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PacienteService pacienteService;

    @Test
    void crearPaciente_exito() throws Exception {
        IdMessageDTO respuesta = new IdMessageDTO(1, "Paciente creado de manera exitosa: ");
        Mockito.when(pacienteService.crearPaciente(any(PacienteDTO.class)))
                .thenReturn(ResponseEntity.ok(respuesta));

        String json = """
            {
                "nombre": "Juan Perez",
                "cedula": "12345678",
                "fechaNacimiento": "2000-01-01",
                "lateralidad": "Derecha"
            }
            """;

        mockMvc.perform(post("/CrearPaciente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Paciente creado de manera exitosa: "));
    }

    @Test
    void crearPaciente_error() throws Exception {
        Mockito.when(pacienteService.crearPaciente(any(PacienteDTO.class)))
                .thenThrow(new RuntimeException("Error interno"));

        String json = """
            {
                "nombre": "Juan Perez",
                "cedula": "12345678",
                "fechaNacimiento": "2000-01-01",
                "lateralidad": "Derecha"
            }
            """;

        mockMvc.perform(post("/CrearPaciente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void getAllPacientes_exito() throws Exception {
        List<PacienteListadoDTO> lista = List.of(new PacienteListadoDTO(1, "Juan Perez", "12345678"));
        Mockito.when(pacienteService.getAllPacientes()).thenReturn(lista);

        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"))
                .andExpect(jsonPath("$[0].cedula").value("12345678"));
    }

    @Test
    void getAllPacientes_error() throws Exception {
        Mockito.when(pacienteService.getAllPacientes()).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/pacientes"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void getPacienteByCedula_exito() throws Exception {
        IdMessageDTO respuesta = new IdMessageDTO(1, "Paciente encontrado: Juan Perez");
        Mockito.when(pacienteService.getPacienteByCedula("12345678"))
                .thenReturn(ResponseEntity.ok(respuesta));

        mockMvc.perform(get("/pacientes/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Paciente encontrado: Juan Perez"));
    }

    @Test
    void getPacienteByCedula_error() throws Exception {
        Mockito.when(pacienteService.getPacienteByCedula("12345678"))
                .thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/pacientes/12345678"))
                .andExpect(status().is5xxServerError());
    }
}