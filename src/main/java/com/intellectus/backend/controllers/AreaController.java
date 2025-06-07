package com.intellectus.backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.intellectus.backend.dto.AreaDTO;
import com.intellectus.backend.servicios.AreaService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping("/areas")
    public List<AreaDTO> getAreas() {
        return areaService.getAllAreas();
    }
}