package com.intellectus.backend.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "transtorno_general")
public class TranstornoGeneral implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_de_transtorno", nullable = false)
    private TipoDeTranstorno tipoDeTranstorno;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "transtornoGeneral", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TranstornoEspecifico> transtornosEspecificos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoDeTranstorno getTipoDeTranstorno() {
        return tipoDeTranstorno;
    }

    public void setTipoDeTranstorno(TipoDeTranstorno tipoDeTranstorno) {
        this.tipoDeTranstorno = tipoDeTranstorno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<TranstornoEspecifico> getTranstornosEspecificos() {
        return transtornosEspecificos;
    }

    public void setTranstornosEspecificos(List<TranstornoEspecifico> transtornosEspecificos) {
        this.transtornosEspecificos = transtornosEspecificos;
    }
}