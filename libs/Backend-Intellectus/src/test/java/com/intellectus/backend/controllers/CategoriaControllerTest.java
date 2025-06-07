package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.CategoriaDTO;
import com.intellectus.backend.servicios.CategoriaService;
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

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @Test
    void getCategoriasByAreaId_exito() throws Exception {
        List<CategoriaDTO> lista = List.of(new CategoriaDTO(1, "Cognitiva", "TipoA"));
        Mockito.when(categoriaService.getCategoriasByAreaId(1)).thenReturn(lista);

        mockMvc.perform(get("/categoria/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Cognitiva"))
                .andExpect(jsonPath("$[0].tipoResultado").value("TipoA"));
    }

    @Test
    void getCategoriasByAreaId_error() throws Exception {
        Mockito.when(categoriaService.getCategoriasByAreaId(1)).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/categoria/1"))
                .andExpect(status().is5xxServerError());
    }
}
