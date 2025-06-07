package com.intellectus.backend.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "pregunta_formula")
public class PreguntaFormula implements Serializable {

    @Id
    @Column(name = "id_pregunta")
    private Integer idPregunta;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_pregunta")
    private Pregunta pregunta;

    @Column(name = "origen", length = 50)
    private String origen;

    @Column(name = "formula", length = 50)
    private String formula;

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
        this.idPregunta = pregunta.getId();
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}