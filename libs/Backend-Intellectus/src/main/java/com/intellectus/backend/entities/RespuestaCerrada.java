package com.intellectus.backend.entities;

import java.io.Serializable;
import com.intellectus.backend.entities.id.Respuesta;
import jakarta.persistence.*;

@Entity
@Table(name = "respuesta_cerrada")
public class RespuestaCerrada implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_opcion_listado", nullable = false)
    private OpcionListado opcion;

    public Consulta getConsulta() {
        return consulta;
    }

    public Respuesta getId() {
        return id;
    }

    public OpcionListado getOpcion() {
        return opcion;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public void setId(Respuesta id) {
        this.id = id;
    }

    public void setOpcion(OpcionListado opcion) {
        this.opcion = opcion;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
}