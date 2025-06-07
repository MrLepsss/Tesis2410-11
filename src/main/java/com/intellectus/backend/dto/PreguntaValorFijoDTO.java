package com.intellectus.backend.dto;

public class PreguntaValorFijoDTO extends PreguntaDTO {
    private String valor;

    public PreguntaValorFijoDTO() {
    }

    public PreguntaValorFijoDTO(Integer idPregunta, String textoPregunta, String tipoRespuesta, Integer idCategoria) {
        super(idPregunta, textoPregunta, tipoRespuesta, idCategoria);
    }

    public PreguntaValorFijoDTO(Integer idPregunta, String textoPregunta, String tipoRespuesta, Integer idCategoria,
            String valor) {
        super(idPregunta, textoPregunta, tipoRespuesta, idCategoria);
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}