package com.intellectus.backend.entities.id;
import java.io.Serializable;
import java.util.Objects;

public class RespuestaGraficoId implements Serializable {

    private Integer idConsulta;
    private Integer idParteGrafico;

    public RespuestaGraficoId() {}
    public RespuestaGraficoId(Integer idConsulta, Integer idParteGrafico) {
        this.idConsulta = idConsulta;
        this.idParteGrafico = idParteGrafico;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RespuestaGraficoId)) return false;
        RespuestaGraficoId that = (RespuestaGraficoId) o;
        return Objects.equals(idConsulta, that.idConsulta) &&
               Objects.equals(idParteGrafico, that.idParteGrafico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConsulta, idParteGrafico);
    }
}

