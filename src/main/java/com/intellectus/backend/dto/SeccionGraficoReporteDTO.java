package com.intellectus.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class SeccionGraficoReporteDTO {
    private String seccion;
    private String color;
    private List<PreguntaReporteDTO> partes;

    public SeccionGraficoReporteDTO() {
        this.partes = new ArrayList<>();
    }

    public SeccionGraficoReporteDTO(String seccion, String color, List<PreguntaReporteDTO> partes) {
        this.seccion = seccion;
        this.color = color;
        this.partes = partes != null ? partes : new ArrayList<>();
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<PreguntaReporteDTO> getPartes() {
        return partes;
    }

    public void setPartes(List<PreguntaReporteDTO> partes) {
        this.partes = partes;
    }
}