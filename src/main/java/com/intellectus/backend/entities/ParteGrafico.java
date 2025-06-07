package com.intellectus.backend.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "parte_grafico")
public class ParteGrafico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "texto", nullable = false, length = 255)
    private String texto;

    @Column(name = "valor", nullable = false, length = 255)
    private String valor;

    @ManyToOne
    @JoinColumn(name = "id_seccion_grafico", nullable = false)
    private SeccionGrafico seccionGrafico;

    @OneToOne(mappedBy = "parteGrafico", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private GraficoReferencia graficoReferencia;

    @OneToMany(mappedBy = "parteGrafico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RespuestaGrafico> respuestasGrafico = new ArrayList<>();

    @OneToOne(mappedBy = "parteGrafico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private GraficaFormula graficaFormula;

    public Integer getId() {
        return id;
    }

    public SeccionGrafico getSeccionGrafico() {
        return seccionGrafico;
    }

    public String getTexto() {
        return texto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSeccionGrafico(SeccionGrafico seccionGrafico) {
        this.seccionGrafico = seccionGrafico;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setRespuestasGrafico(List<RespuestaGrafico> respuestasGrafico) {
        this.respuestasGrafico = respuestasGrafico;
    }

    public List<RespuestaGrafico> getRespuestasGrafico() {
        return respuestasGrafico;
    }
    public GraficoReferencia getGraficoReferencia() {
        return graficoReferencia;
    }
    public void setGraficoReferencia(GraficoReferencia graficoReferencia) {
        this.graficoReferencia = graficoReferencia;
    }
    public void setGraficaFormula(GraficaFormula graficaFormula) {
        this.graficaFormula = graficaFormula;
    }
    public GraficaFormula getGraficaFormula() {
        return graficaFormula;
    }
}
