package com.intellectus.backend.entities;

import java.io.Serializable;
import com.intellectus.backend.entities.id.ConsultaMedicoId;
import jakarta.persistence.*;

@Entity
@Table(name = "consulta_medico")
public class ConsultaMedico implements Serializable {

    @EmbeddedId
    private ConsultaMedicoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idConsulta")
    @JoinColumn(name = "id_consulta", nullable = false)
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMedico")
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    public Consulta getConsulta() {
        return consulta;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setId(ConsultaMedicoId id) {
        this.id = id;
    }

    public ConsultaMedicoId getId() {
        return id;
    }
}