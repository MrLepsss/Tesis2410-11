package com.intellectus.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class GraficoReporteDTO extends CategoriaReporteDTO {
    private String tipo;
    private List<SeccionGraficoReporteDTO> secciones;

    public GraficoReporteDTO() {
        this.secciones = new ArrayList<>();
    }

    public GraficoReporteDTO(String nombre, String tipo, List<SeccionGraficoReporteDTO> secciones) {
        super(nombre);
        this.tipo = tipo;
        this.secciones = secciones != null ? secciones : new ArrayList<>();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<SeccionGraficoReporteDTO> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<SeccionGraficoReporteDTO> secciones) {
        this.secciones = secciones;
    }
}