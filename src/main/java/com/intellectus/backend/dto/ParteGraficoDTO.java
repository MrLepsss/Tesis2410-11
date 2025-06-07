package com.intellectus.backend.dto;

public class ParteGraficoDTO {

    Integer id;
    String texto;
    String tipoValor;
    String valor;

    public ParteGraficoDTO(Integer id, String texto,String tipoValor,String valor) {
        this.id = id;
        this.texto = texto;
        this.tipoValor = tipoValor;
        this.valor = valor;
    }
    public ParteGraficoDTO() {
    }
    public String getTexto() {
        return texto;
    }
    public String getTipoValor() {
        return tipoValor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getValor() {
        return valor;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
}