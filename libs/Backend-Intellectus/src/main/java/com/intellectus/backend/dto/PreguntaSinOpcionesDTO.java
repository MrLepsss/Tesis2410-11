package com.intellectus.backend.dto;

public class PreguntaSinOpcionesDTO extends PreguntaDTO {

    String valorAlmacenado;

    public PreguntaSinOpcionesDTO() {
    }

    public PreguntaSinOpcionesDTO(Integer idPregunta, String textoPregunta, String tipoRespuesta, Integer idCategoria) {
        super(idPregunta, textoPregunta, tipoRespuesta, idCategoria);
        this.valorAlmacenado=null;
    }

    public String getValorAlmacenado() {
        return valorAlmacenado;
    }
    public void setValorAlmacenado(String valorAlmacenado) {
        this.valorAlmacenado = valorAlmacenado;
    }
}