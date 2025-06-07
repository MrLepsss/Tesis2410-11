package com.intellectus.backend.dto;

import java.util.List;

public class PreguntaConOpcionesDTO extends PreguntaDTO {
    private List<OpcionListadoDTO> opciones;
    private Integer opcionSeleccionada;

    public PreguntaConOpcionesDTO(Integer idPregunta, String textoPregunta, String tipoRespuesta, Integer idCategoria) {
        super(idPregunta, textoPregunta, tipoRespuesta, idCategoria);
        this.opcionSeleccionada=null;
    }

    public List<OpcionListadoDTO> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<OpcionListadoDTO> opciones) {
        this.opciones = opciones;
    }
    public Integer getOpcionSeleccionada() {
        return opcionSeleccionada;
    }
    public void setOpcionSeleccionada(Integer opcionSeleccionada) {
        this.opcionSeleccionada = opcionSeleccionada;
    }
}