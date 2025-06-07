package com.intellectus.backend.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "pregunta_referencia")
public class PreguntaReferencia implements Serializable {

    @Id
    @Column(name = "id_pregunta")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_pregunta", referencedColumnName = "id", nullable = false)
    private Pregunta pregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_referencia", nullable = false)
    private Referencia referencia;

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Referencia getReferencia() {
        return referencia;
    }

    public void setReferencia(Referencia referencia) {
        this.referencia = referencia;
    }
}