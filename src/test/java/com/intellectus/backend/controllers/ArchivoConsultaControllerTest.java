package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.FilesDTO;
import com.intellectus.backend.servicios.ArchivoConsultaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArchivoConsultaController.class)
class ArchivoConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArchivoConsultaService archivoConsultaService;

    @Test
    void obtenerArchivosPorPacienteId_exito() throws Exception {
        List<FilesDTO> lista = List.of(new FilesDTO(1, "archivo1.pdf", "PDF"));
        Mockito.when(archivoConsultaService.obtenerArchivosPorPacienteId(1L)).thenReturn(lista);

        mockMvc.perform(get("/api/archivos/paciente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreArchivo").value("archivo1.pdf"))
                .andExpect(jsonPath("$[0].tipoArchivo").value("PDF"));
    }

    @Test
    void obtenerArchivosPorPacienteId_error() throws Exception {
        Mockito.when(archivoConsultaService.obtenerArchivosPorPacienteId(1L)).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/api/archivos/paciente/1"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void obtenerArchivosPorPacienteCedula_exito() throws Exception {
        List<FilesDTO> lista = List.of(new FilesDTO(2, "archivo2.jpg", "Imagen"));
        Mockito.when(archivoConsultaService.obtenerArchivosPorPacienteCedula("12345678")).thenReturn(lista);

        mockMvc.perform(get("/api/archivos/paciente/cedula/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].nombreArchivo").value("archivo2.jpg"))
                .andExpect(jsonPath("$[0].tipoArchivo").value("Imagen"));
    }

    @Test
    void obtenerArchivosPorPacienteCedula_error() throws Exception {
        Mockito.when(archivoConsultaService.obtenerArchivosPorPacienteCedula("12345678")).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/api/archivos/paciente/cedula/12345678"))
                .andExpect(status().is5xxServerError());
    }
}
