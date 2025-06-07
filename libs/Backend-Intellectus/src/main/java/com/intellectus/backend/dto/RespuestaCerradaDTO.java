package com.intellectus.backend.dto;

public class RespuestaCerradaDTO extends RespuestaDTO {
    private Integer idOpcionListado;

    public RespuestaCerradaDTO() {
    }

    public RespuestaCerradaDTO(Integer idConsulta, Integer idPregunta, Integer idOpcionListado) {
        super(idConsulta, idPregunta);
        this.idOpcionListado = idOpcionListado;
    }

    public Integer getIdOpcionListado() {
        return idOpcionListado;
    }

    public void setIdOpcionListado(Integer idOpcionListado) {
        this.idOpcionListado = idOpcionListado;
    }
}