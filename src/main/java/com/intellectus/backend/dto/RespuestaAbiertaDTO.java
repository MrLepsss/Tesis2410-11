package com.intellectus.backend.dto;

public class RespuestaAbiertaDTO extends RespuestaDTO {
    private String valor;

    public RespuestaAbiertaDTO() {
    }

    public RespuestaAbiertaDTO(String valor, Integer idConsulta, Integer idPregunta) {
        super(idConsulta, idPregunta);
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}