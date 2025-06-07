package com.intellectus.backend.dto;

public class TranstornoEspecificoDTO {

    private Integer id;
    private String dsmV;
    private String cie10;
    private String nombre;

    public TranstornoEspecificoDTO(Integer id, String dsmV, String cie10, String nombre) {
        this.id = id;
        this.cie10 = cie10;
        this.dsmV = dsmV;
        this.nombre = nombre;
    }

    public void setCie10(String cie10) {
        this.cie10 = cie10;
    }

    public void setDsmV(String dsmV) {
        this.dsmV = dsmV;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCie10() {
        return cie10;
    }

    public String getDsmV() {
        return dsmV;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}