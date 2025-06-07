package com.intellectus.backend.entities;

import java.io.Serializable;
import com.intellectus.backend.entities.id.TranstornoEspecificoConsultaId;
import jakarta.persistence.*;

@Entity
@Table(name = "transtorno_especifico_consulta")
public class TranstornoEspecificoConsulta implements Serializable {

    @EmbeddedId
    private TranstornoEspecificoConsultaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idConsulta")
    @JoinColumn(name = "id_consulta", nullable = false)
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTranstornoEspecifico")
    @JoinColumn(name = "id_transtorno_especifico", nullable = false)
    private TranstornoEspecifico transtornoEspecifico;

    public TranstornoEspecificoConsultaId getId() {
        return id;
    }

    public void setId(TranstornoEspecificoConsultaId id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public TranstornoEspecifico getTranstornoEspecifico() {
        return transtornoEspecifico;
    }

    public void setTranstornoEspecifico(TranstornoEspecifico transtornoEspecifico) {
        this.transtornoEspecifico = transtornoEspecifico;
    }
}