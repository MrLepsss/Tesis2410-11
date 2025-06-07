package com.intellectus.backend.entities.id;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class ConsultaMedicoId implements Serializable {

    private Integer idConsulta;
    private Integer idMedico;

    public ConsultaMedicoId() {
    }

    public ConsultaMedicoId(Integer idConsulta, Integer idMedico) {
        this.idConsulta = idConsulta;
        this.idMedico = idMedico;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ConsultaMedicoId))
            return false;
        ConsultaMedicoId that = (ConsultaMedicoId) o;
        return Objects.equals(idConsulta, that.idConsulta) &&
                Objects.equals(idMedico, that.idMedico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConsulta, idMedico);
    }
}