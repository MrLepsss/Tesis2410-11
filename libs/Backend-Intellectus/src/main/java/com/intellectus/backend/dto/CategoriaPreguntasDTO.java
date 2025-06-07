package com.intellectus.backend.dto;

import java.util.List;
import java.util.ArrayList;

public class CategoriaPreguntasDTO extends CategoriaReporteDTO {
    private List<PreguntaReporteDTO> preguntas;

    public CategoriaPreguntasDTO() {
        preguntas = new ArrayList<>();
    }

    public CategoriaPreguntasDTO(String nombre, List<PreguntaReporteDTO> preguntas) {
        super(nombre);
        this.preguntas = preguntas;
    }

    public List<PreguntaReporteDTO> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaReporteDTO> preguntas) {
        this.preguntas = preguntas;
    }
}
