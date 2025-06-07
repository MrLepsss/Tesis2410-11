package com.intellectus.backend.entities.id;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class TranstornoEspecificoConsultaId implements Serializable {
    
    private Integer idConsulta;
    private Integer idTranstornoEspecifico;

    public TranstornoEspecificoConsultaId() {}

    public TranstornoEspecificoConsultaId(Integer idConsulta, Integer idTranstornoEspecifico) {
        this.idConsulta = idConsulta;
        this.idTranstornoEspecifico = idTranstornoEspecifico;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Integer getIdTranstornoEspecifico() {
        return idTranstornoEspecifico;
    }

    public void setIdTranstornoEspecifico(Integer idTranstornoEspecifico) {
        this.idTranstornoEspecifico = idTranstornoEspecifico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranstornoEspecificoConsultaId)) return false;
        TranstornoEspecificoConsultaId that = (TranstornoEspecificoConsultaId) o;
        return Objects.equals(idConsulta, that.idConsulta) &&
               Objects.equals(idTranstornoEspecifico, that.idTranstornoEspecifico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConsulta, idTranstornoEspecifico);
    }
}