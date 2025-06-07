package com.intellectus.backend.repositorios;

import com.intellectus.backend.entities.Grafica;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface GraficaRepository extends CrudRepository<Grafica, Integer> {

    @EntityGraph(attributePaths = {
            "secciones",
            "secciones.partesGrafico",
            "secciones.partesGrafico.graficoReferencia",
            "secciones.partesGrafico.graficoReferencia.referencia",
            "secciones.partesGrafico.graficaFormula",
            "secciones.partesGrafico.graficaFormula.formula",
            "secciones.partesGrafico.respuestasGrafico"
    })
    Optional<Grafica> findById(Integer id);
}