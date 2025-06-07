package com.intellectus.backend.controllers;

import org.springframework.web.bind.annotation.*;
import com.intellectus.backend.dto.CategoriaDTO;
import com.intellectus.backend.servicios.CategoriaService;
import java.util.List;

@RestController
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/categoria/{areaId}")
    public List<CategoriaDTO> getCategoriasByAreaId(@PathVariable Integer areaId) {
        return categoriaService.getCategoriasByAreaId(areaId);
    }
}