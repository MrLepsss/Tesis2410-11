package com.intellectus.backend.dto;

public class AreaDTO {
    private Integer id;
    private String nombre;

    public AreaDTO() {
    }

    public AreaDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}