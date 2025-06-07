package com.intellectus.backend.dto;

public class CategoriaDTO {
    private Integer id;
    private String nombre;
    private String tipoResultado;

    public CategoriaDTO() {
    }
    
    public CategoriaDTO(Integer id, String nombre, String tipoResultado) {
        this.id = id;
        this.nombre = nombre;
        this.tipoResultado = tipoResultado;
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

    public String getTipoResultado() {
        return tipoResultado;
    }

    public void setTipoResultado(String tipoResultado) {
        this.tipoResultado = tipoResultado;
    }
}