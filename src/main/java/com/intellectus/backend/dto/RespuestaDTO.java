package com.intellectus.backend.dto;

public class RespuestaDTO {

    private Integer idConsulta;
    private Integer idPregunta;

    public RespuestaDTO() {
    }

    public RespuestaDTO(Integer idConsulta, Integer idPregunta) {
        this.idConsulta = idConsulta;
        this.idPregunta = idPregunta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Integer getIdPregunta() {
        return idPregunta;
    }
}