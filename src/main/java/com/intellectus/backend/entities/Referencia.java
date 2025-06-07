package com.intellectus.backend.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "referencias")
public class Referencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "origen", length = 50, nullable = false)
    private String origen;

    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;

    @Column(name = "nombre", length = 255, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "referencia", fetch = FetchType.LAZY)
    private List<PreguntaReferencia> preguntaReferencia = new ArrayList<>();

    @OneToMany(mappedBy = "referencia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Valor> valores = new ArrayList<>();

    @OneToMany(mappedBy = "referencia",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<GraficoReferencia> graficoReferencias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "valor_tomar_pregunta", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_valor_tomar_pregunta"), nullable = true)
    private Pregunta valorTomarPregunta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PreguntaReferencia> getPreguntaReferencia() {
        return preguntaReferencia;
    }

    public void setPreguntaReferencia(List<PreguntaReferencia> preguntaReferencia) {
        this.preguntaReferencia = preguntaReferencia;
    }

    public List<Valor> getValores() {
        return valores;
    }

    public void setValores(List<Valor> valores) {
        this.valores = valores;
    }

    public Pregunta getValorTomarPregunta() {
        return valorTomarPregunta;
    }

    public void setValorTomarPregunta(Pregunta valorTomarPregunta) {
        this.valorTomarPregunta = valorTomarPregunta;
    }
    public void setGraficoReferencias(List<GraficoReferencia> graficoReferencias) {
        this.graficoReferencias = graficoReferencias;
    }
    public List<GraficoReferencia> getGraficoReferencias() {
        return graficoReferencias;
    }
}