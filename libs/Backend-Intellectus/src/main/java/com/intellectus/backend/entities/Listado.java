package com.intellectus.backend.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "listados")
public class Listado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255)
    private String nombre;

    @OneToMany(mappedBy = "listado", fetch = FetchType.LAZY)
    private List<PreguntaListado> preguntasListado = new ArrayList<>();

    @OneToMany(mappedBy = "listado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OpcionListado> opciones = new ArrayList<>();

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

    public List<PreguntaListado> getPreguntasListado() {
        return preguntasListado;
    }

    public void setPreguntasListado(List<PreguntaListado> preguntas) {
        this.preguntasListado = preguntas;
    }

    public List<OpcionListado> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<OpcionListado> opciones) {
        this.opciones = opciones;
    }
}