package com.intellectus.backend.dto;

public class PreguntaReporteDTO {
    private String pregunta;
    private String respuesta;

    public PreguntaReporteDTO() {
    }

    public PreguntaReporteDTO(String pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}