package com.intellectus.backend.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "grafica_formula")
public class GraficaFormula implements Serializable {

    @Id
    @Column(name = "id_parte_grafico")
    private Integer idPregunta;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_parte_grafico")
    private ParteGrafico parteGrafico;

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

   public ParteGrafico getParteGrafico() {
       return parteGrafico;
   }
   public void setParteGrafico(ParteGrafico parteGrafico) {
       this.parteGrafico = parteGrafico;
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