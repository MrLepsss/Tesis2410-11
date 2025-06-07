package com.intellectus.backend.entities;
import java.io.Serializable;

import com.intellectus.backend.entities.id.RespuestaGraficoId;

import jakarta.persistence.*;

@Entity
@Table(name = "respuesta_grafico")
public class RespuestaGrafico implements Serializable {

    @EmbeddedId
    private RespuestaGraficoId id;

    @Column(length = 255)
    private String valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idConsulta")
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idParteGrafico")
    @JoinColumn(name = "id_parte_grafico")
    private ParteGrafico parteGrafico;

    public Consulta getConsulta() {
        return consulta;
    }
    public RespuestaGraficoId getId() {
        return id;
    }
    public void setId(RespuestaGraficoId id) {
        this.id = id;
    }
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public ParteGrafico getParteGrafico() {
        return parteGrafico;
    }
    public void setParteGrafico(ParteGrafico parteGrafico) {
        this.parteGrafico = parteGrafico;
    }
}

