package com.intellectus.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class ReporteDTO {
    private List<AreaReporteDTO> areas;

    public ReporteDTO() {
        areas= new ArrayList<>();
    }

    public ReporteDTO(List<AreaReporteDTO> areas) {
        this.areas = areas;
    }

    public List<AreaReporteDTO> getAreas() {
        return areas;
    }
    public void setAreas(List<AreaReporteDTO> areas) {
        this.areas = areas;
    }
}