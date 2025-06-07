package com.intellectus.backend.entities;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "grafica")
public class Grafica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "tipo", length = 100)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "grafica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeccionGrafico> secciones;

    public Categoria getCategoria() {
        return categoria;
    }
    public Integer getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setSecciones(List<SeccionGrafico> secciones) {
        this.secciones = secciones;
    }
    public List<SeccionGrafico> getSecciones() {
        return secciones;
    }
}