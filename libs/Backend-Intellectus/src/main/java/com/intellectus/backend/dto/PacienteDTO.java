package com.intellectus.backend.dto;

import java.time.LocalDate;

public class PacienteDTO {

    private String nombre;
    private String cedula;
    private LocalDate fechaNacimiento;
    private String lateralidad;

    public PacienteDTO() {
    }

    public PacienteDTO(String nombre, String cedula, LocalDate fechaNacimiento, String lateralidad) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.fechaNacimiento = fechaNacimiento;
        this.lateralidad = lateralidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getLateralidad() {
        return lateralidad;
    }

    public void setLateralidad(String lateralidad) {
        this.lateralidad = lateralidad;
    }
}