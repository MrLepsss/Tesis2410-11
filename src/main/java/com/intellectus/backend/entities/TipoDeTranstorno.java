package com.intellectus.backend.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tipo_de_transtorno")
public class TipoDeTranstorno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "tipoDeTranstorno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TranstornoGeneral> transtornosGenerales;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<TranstornoGeneral> getTranstornosGenerales() {
        return transtornosGenerales;
    }

    public void setTranstornosGenerales(List<TranstornoGeneral> transtornosGenerales) {
        this.transtornosGenerales = transtornosGenerales;
    }
}