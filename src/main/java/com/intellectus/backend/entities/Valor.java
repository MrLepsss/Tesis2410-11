package com.intellectus.backend.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "valores")
public class Valor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_referencia", nullable = false)
    private Referencia referencia;

    private String condicional;

    @Column(name = "valor_condicional")
    private String valorCondicional;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Referencia getReferencia() {
        return referencia;
    }

    public void setReferencia(Referencia referencia) {
        this.referencia = referencia;
    }

    public String getCondicional() {
        return condicional;
    }

    public void setCondicional(String condicional) {
        this.condicional = condicional;
    }

    public String getValorCondicional() {
        return valorCondicional;
    }

    public void setValorCondicional(String valorCondicional) {
        this.valorCondicional = valorCondicional;
    }
}