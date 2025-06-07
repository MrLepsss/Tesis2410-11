package com.intellectus.backend.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "preguntas")
public class Pregunta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "texto_pregunta", length = 500, nullable = false)
    private String textoPregunta;

    @Column(name = "tipo_respuesta", length = 50, nullable = false)
    private String tipoRespuesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @OneToOne(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PreguntaListado preguntaListado;

    @OneToOne(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PreguntaReferencia preguntaReferencia;

    @OneToOne(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PreguntaFormula preguntaFormula;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RespuestaAbierta> respuestasAbiertas = new HashSet<>();

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RespuestaCerrada> respuestasCerradas = new HashSet<>();

    @Column(name = "visibilidad", nullable = false)
    private boolean visibilidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    public String getTipoRespuesta() {
        return tipoRespuesta;
    }

    public void setTipoRespuesta(String tipoRespuesta) {
        this.tipoRespuesta = tipoRespuesta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public PreguntaListado getPreguntaListado() {
        return preguntaListado;
    }

    public void setPreguntaListado(PreguntaListado preguntaListado) {
        this.preguntaListado = preguntaListado;
    }

    public void addRespuestaAbierta(RespuestaAbierta respuesta) {
        respuestasAbiertas.add(respuesta);
        respuesta.setPregunta(this);
    }

    public void removeRespuestaAbierta(RespuestaAbierta respuesta) {
        respuestasAbiertas.remove(respuesta);
        respuesta.setPregunta(null);
    }

    public PreguntaReferencia getPreguntaReferencia() {
        return preguntaReferencia;
    }

    public void setPreguntaReferencia(PreguntaReferencia preguntaReferencia) {
        this.preguntaReferencia = preguntaReferencia;
    }

    public PreguntaFormula getPreguntaFormula() {
        return preguntaFormula;
    }

    public void setPreguntaFormula(PreguntaFormula preguntaFormula) {
        this.preguntaFormula = preguntaFormula;
    }
    public Set<RespuestaAbierta> getRespuestasAbiertas() {
        return respuestasAbiertas;
    }
    public Set<RespuestaCerrada> getRespuestasCerradas() {
        return respuestasCerradas;
    }
    public void setRespuestasAbiertas(Set<RespuestaAbierta> respuestasAbiertas) {
        this.respuestasAbiertas = respuestasAbiertas;
    }
    public void setRespuestasCerradas(Set<RespuestaCerrada> respuestasCerradas) {
        this.respuestasCerradas = respuestasCerradas;
    }
    public boolean isVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }
}