package com.intellectus.backend.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "transtorno_especifico")
public class TranstornoEspecifico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_transtorno_general", nullable = false)
    private TranstornoGeneral transtornoGeneral;

    @Column(name = "DSM_V")
    private String dsmV;

    @Column(name = "CIE_10")
    private String cie10;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "transtornoEspecifico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TranstornoEspecificoConsulta> consultasRelacionadas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TranstornoGeneral getTranstornoGeneral() {
        return transtornoGeneral;
    }

    public void setTranstornoGeneral(TranstornoGeneral transtornoGeneral) {
        this.transtornoGeneral = transtornoGeneral;
    }

    public String getDsmV() {
        return dsmV;
    }

    public void setDsmV(String dsmV) {
        this.dsmV = dsmV;
    }

    public String getCie10() {
        return cie10;
    }

    public void setCie10(String cie10) {
        this.cie10 = cie10;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<TranstornoEspecificoConsulta> getConsultasRelacionadas() {
        return consultasRelacionadas;
    }

    public void setConsultasRelacionadas(List<TranstornoEspecificoConsulta> consultasRelacionadas) {
        this.consultasRelacionadas = consultasRelacionadas;
    }
}