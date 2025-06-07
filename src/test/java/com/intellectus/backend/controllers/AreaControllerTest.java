package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.AreaDTO;
import com.intellectus.backend.servicios.AreaService;
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

@WebMvcTest(AreaController.class)
class AreaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AreaService areaService;

    @Test
    void getAreas_exito() throws Exception {
        List<AreaDTO> lista = List.of(new AreaDTO(1, "Área Cognitiva"));
        Mockito.when(areaService.getAllAreas()).thenReturn(lista);

        mockMvc.perform(get("/areas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Área Cognitiva"));
    }

    @Test
    void getAreas_error() throws Exception {
        Mockito.when(areaService.getAllAreas()).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/areas"))
                .andExpect(status().is5xxServerError());
    }
}
