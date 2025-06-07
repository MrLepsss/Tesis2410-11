package com.intellectus.backend.dto;

public class CategoriaAcumuladaDTO extends CategoriaReporteDTO {
    private String respuesta;

    public CategoriaAcumuladaDTO() {
        super();
    }

    public CategoriaAcumuladaDTO(String nombre, String respuesta) {
        super(nombre);
        this.respuesta = respuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
