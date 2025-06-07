package com.intellectus.backend.entities;
import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "grafico_referencia")
public class GraficoReferencia implements Serializable {

    @Id
    @Column(name = "id_parte_grafico")
    private Integer idParteGrafico;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_parte_grafico")
    private ParteGrafico parteGrafico;

    @ManyToOne
    @JoinColumn(name = "id_referencia", nullable = false)
    private Referencia referencia;

    public Integer getIdParteGrafico() {
        return idParteGrafico;
    }
    public ParteGrafico getParteGrafico() {
        return parteGrafico;
    }
    public Referencia getReferencia() {
        return referencia;
    }
    public void setIdParteGrafico(Integer idParteGrafico) {
        this.idParteGrafico = idParteGrafico;
    }
    public void setParteGrafico(ParteGrafico parteGrafico) {
        this.parteGrafico = parteGrafico;
    }
    public void setReferencia(Referencia referencia) {
        this.referencia = referencia;
    }
}

