package com.intellectus.backend.entities.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Respuesta implements Serializable {

    private Integer idConsulta;
    private Integer idPregunta;

    public Respuesta() {
    }

    public Respuesta(Integer idConsulta, Integer idPregunta) {
        this.idConsulta = idConsulta;
        this.idPregunta = idPregunta;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Respuesta))
            return false;
        Respuesta that = (Respuesta) o;
        return Objects.equals(idConsulta, that.idConsulta) &&
                Objects.equals(idPregunta, that.idPregunta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConsulta, idPregunta);
    }
}