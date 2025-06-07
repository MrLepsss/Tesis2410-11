package com.intellectus.backend.dto;

public class RespuestaGraficoDTO {

    private Integer idConsulta;
    private Integer idParteGrafico;
    private String valor;

    public RespuestaGraficoDTO() {
    }

    public RespuestaGraficoDTO(Integer idConsulta, Integer idParteGrafico,String valor) {
        this.idConsulta = idConsulta;
        this.idParteGrafico = idParteGrafico;
        this.valor = valor;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdParteGrafico(Integer idParteGrafico) {
        this.idParteGrafico = idParteGrafico;
    }
    public Integer getIdParteGrafico() {
        return idParteGrafico;
    }
   public void setValor(String valor) {
       this.valor = valor;
   }
   public String getValor() {
       return valor;
   }
}