package com.intellectus.backend.dto;

public class IdMessageDTO {
    private Integer id;
    private String mensaje;

    public IdMessageDTO() {
    }

    public IdMessageDTO(Integer id, String mensaje) {
        this.id = id;
        this.mensaje = mensaje;
    }

    public Integer getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}