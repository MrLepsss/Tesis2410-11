package com.intellectus.backend.dto;

public class OpcionListadoDTO {
    private Integer id;
    private String texto;
    private String tipoValor;
    private String valor;

    public OpcionListadoDTO() {
    }

    public OpcionListadoDTO(Integer id, String texto, String tipoValor, String valor) {
        this.id = id;
        this.texto = texto;
        this.tipoValor = tipoValor;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}