package com.intellectus.backend.dto;

import java.util.List;

public class GraficoDTO extends PreguntaDTO {

    String tipo;
    List<SeccionGraficoDTO> secciones;

    public GraficoDTO(Integer idPregunta, String textoPregunta, String tipoRespuesta, Integer idCategoria) {
        super(idPregunta, textoPregunta, tipoRespuesta, idCategoria);
        this.tipo = null;
        this.secciones = null;
    }
    public GraficoDTO() {
        this.tipo = null;
        this.secciones = null;
    }
    public List<SeccionGraficoDTO> getSecciones() {
        return secciones;
    }
    public String getTipo() {
        return tipo;
    }
    public void setSecciones(List<SeccionGraficoDTO> secciones) {
        this.secciones = secciones;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
