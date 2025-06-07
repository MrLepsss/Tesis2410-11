package com.intellectus.backend.dto;

import java.util.List;

public class SeccionGraficoDTO {

    String nombre;
    String color;
    List<ParteGraficoDTO> partes;

    public SeccionGraficoDTO(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
        this.partes = null;
    }
    public SeccionGraficoDTO() {
        this.nombre = null;
        this.color = null;
        this.partes = null;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPartes(List<ParteGraficoDTO> partes) {
        this.partes = partes;
    }
    public String getColor() {
        return color;
    }
    public String getNombre() {
        return nombre;
    }
    public List<ParteGraficoDTO> getPartes() {
        return partes;
    }
}
