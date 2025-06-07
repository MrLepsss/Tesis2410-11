package com.intellectus.backend.dto;

import java.time.LocalDate;

public class ConsultaDTO {

    Integer id;
    LocalDate fecha;

    public ConsultaDTO(Integer id, LocalDate fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Integer getId() {
        return id;
    }
}