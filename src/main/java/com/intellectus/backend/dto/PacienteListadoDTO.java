package com.intellectus.backend.dto;

public class PacienteListadoDTO {

    private Integer id;
    private String nombre;
    private String cedula;

    public PacienteListadoDTO() {
    }

    public PacienteListadoDTO(Integer id, String nombre, String cedula) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }
}