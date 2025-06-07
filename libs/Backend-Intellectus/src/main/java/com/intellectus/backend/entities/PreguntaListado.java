package com.intellectus.backend.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "pregunta_listado")
public class PreguntaListado implements Serializable {

    @Id
    @Column(name = "id_pregunta")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_pregunta", referencedColumnName = "id", nullable = false)
    private Pregunta pregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_listado", nullable = false)
    private Listado listado;

    public Listado getListado() {
        return listado;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setListado(Listado listado) {
        this.listado = listado;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
}