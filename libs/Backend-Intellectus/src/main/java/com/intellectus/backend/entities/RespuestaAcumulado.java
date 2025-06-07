package com.intellectus.backend.entities;

import com.intellectus.backend.entities.id.AcumuladoCategoriaId;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "respuesta_acumulado")
public class RespuestaAcumulado implements Serializable {

    @EmbeddedId
    private AcumuladoCategoriaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idConsulta")
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCategoria")
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @Column(name = "valor", length = 3000)
    private String valor;

    public AcumuladoCategoriaId getId() {
        return id;
    }

    public void setId(AcumuladoCategoriaId id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
}