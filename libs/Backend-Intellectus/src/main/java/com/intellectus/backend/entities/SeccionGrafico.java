package com.intellectus.backend.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "seccion_grafico")
public class SeccionGrafico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "color", length = 50)
    private String color;

    @ManyToOne
    @JoinColumn(name = "id_grafica", nullable = false)
    private Grafica grafica;

    @OneToMany(mappedBy = "seccionGrafico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ParteGrafico> partesGrafico = new ArrayList<>();

    public String getColor() {
        return color;
    }

    public Grafica getGrafica() {
        return grafica;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setGrafica(Grafica grafica) {
        this.grafica = grafica;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPartesGrafico(List<ParteGrafico> partesGrafico) {
        this.partesGrafico = partesGrafico;
    }
    public List<ParteGrafico> getPartesGrafico() {
        return partesGrafico;
    }
}
