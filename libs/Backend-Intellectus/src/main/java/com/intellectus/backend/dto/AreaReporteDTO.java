package com.intellectus.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class AreaReporteDTO {
    private String nombre;
    private List<CategoriaReporteDTO> categorias;

    public AreaReporteDTO() {
        categorias= new ArrayList<>();
    }

    public AreaReporteDTO(String nombre, List<CategoriaReporteDTO> categorias) {
        this.nombre = nombre;
        this.categorias = categorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CategoriaReporteDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaReporteDTO> categorias) {
        this.categorias = categorias;
    }
}
