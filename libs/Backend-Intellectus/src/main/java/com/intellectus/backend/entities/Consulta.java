package com.intellectus.backend.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultas")
public class Consulta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @Column(name = "fecha_evaluacion", nullable = false)
    private LocalDate fechaEvaluacion;

    @Column(name = "edad_paciente", nullable = false)
    private Integer edadPaciente;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RespuestaAbierta> respuestasAbiertas = new ArrayList<>();

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RespuestaCerrada> respuestasCerradas = new ArrayList<>();

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ConsultaMedico> consultaMedicos = new ArrayList<>();

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<RespuestaGrafico> respuestasGrafico;

    public Consulta() {
    }

    public Consulta(Paciente paciente, LocalDate fechaEvaluacion, Integer edadPaciente) {
        this.paciente = paciente;
        this.fechaEvaluacion = fechaEvaluacion;
        this.edadPaciente = edadPaciente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDate fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public Integer getEdadPaciente() {
        return edadPaciente;
    }

    public void setEdadPaciente(Integer edadPaciente) {
        this.edadPaciente = edadPaciente;
    }

    public List<RespuestaAbierta> getRespuestasAbiertas() {
        return respuestasAbiertas;
    }

    public void setRespuestasAbiertas(List<RespuestaAbierta> respuestasAbiertas) {
        this.respuestasAbiertas = respuestasAbiertas;
    }

    public void addRespuestaAbierta(RespuestaAbierta respuesta) {
        respuestasAbiertas.add(respuesta);
        respuesta.setConsulta(this);
    }

    public void removeRespuestaAbierta(RespuestaAbierta respuesta) {
        respuestasAbiertas.remove(respuesta);
        respuesta.setConsulta(null);
    }

    public List<RespuestaCerrada> getRespuestasCerradas() {
        return respuestasCerradas;
    }

    public void setRespuestasCerradas(List<RespuestaCerrada> respuestasCerradas) {
        this.respuestasCerradas = respuestasCerradas;
    }
    public List<ConsultaMedico> getConsultaMedicos() {
        return consultaMedicos;
    }
    public List<RespuestaGrafico> getRespuestasGrafico() {
        return respuestasGrafico;
    }
    public void setConsultaMedicos(List<ConsultaMedico> consultaMedicos) {
        this.consultaMedicos = consultaMedicos;
    }
    public void setRespuestasGrafico(List<RespuestaGrafico> respuestasGrafico) {
        this.respuestasGrafico = respuestasGrafico;
    }
}