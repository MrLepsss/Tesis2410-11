package com.intellectus.backend.dto;

public class PreguntaDTO {
    private Integer id;
    private String textoPregunta;
    private String tipoRespuesta;
    private Integer idCategoria;

    public PreguntaDTO() {
    }

    public PreguntaDTO(Integer id, String textoPregunta, String tipoRespuesta, Integer idCategoria) {
        this.id = id;
        this.textoPregunta = textoPregunta;
        this.tipoRespuesta = tipoRespuesta;
        this.idCategoria = idCategoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    public String getTipoRespuesta() {
        return tipoRespuesta;
    }

    public void setTipoRespuesta(String tipoRespuesta) {
        this.tipoRespuesta = tipoRespuesta;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
}