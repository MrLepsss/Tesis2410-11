package com.intellectus.backend.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.intellectus.backend.entities.Pregunta;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Integer> {
     @EntityGraph(attributePaths = {
        "categoria",
        "preguntaListado.listado.opciones",
        "preguntaReferencia.referencia",
        "preguntaFormula",
        "respuestasAbiertas.consulta",
        "respuestasCerradas.consulta",
        "respuestasCerradas.opcion"
    })
    @Query("SELECT p FROM Pregunta p JOIN FETCH p.categoria c WHERE c.id = :idCategoria")
    List<Pregunta> findByCategoriaIdConCategoria(@Param("idCategoria") Integer idCategoria);
}