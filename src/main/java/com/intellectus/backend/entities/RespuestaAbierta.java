package com.intellectus.backend.entities;

import jakarta.persistence.*;
import java.io.Serializable;

import com.intellectus.backend.entities.id.Respuesta;

@Entity
@Table(name = "respuesta_abierta")
public class RespuestaAbierta implements Serializable {

    @EmbeddedId
    private Respuesta id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idConsulta")
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPregunta")
    @JoinColumn(name = "id_pregunta")
    private Pregunta pregunta;

    @Column(name = "valor", length = 3000)
    private String valor;

    public Respuesta getId() {
        return id;
    }

    public void setId(Respuesta id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}