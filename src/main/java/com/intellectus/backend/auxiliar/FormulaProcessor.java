package com.intellectus.backend.auxiliar;

import java.util.regex.*;
import java.util.*;

import com.intellectus.backend.entities.ParteGrafico;
import com.intellectus.backend.entities.Pregunta;
import com.intellectus.backend.entities.RespuestaAbierta;
import com.intellectus.backend.entities.RespuestaGrafico;
import com.intellectus.backend.repositorios.ParteGraficoRepository;
import com.intellectus.backend.repositorios.PreguntaRepository;
import com.intellectus.backend.repositorios.RespuestaAbiertaRepository;
import com.intellectus.backend.repositorios.RespuestaGraficoRepository;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FormulaProcessor {

    private static final Pattern PATRON_ID = Pattern.compile("\u00bf(\\d+)\\?");
    private final RespuestaAbiertaRepository respuestaAbiertaRepository;
    private final PreguntaRepository preguntaRepository;
    private final RespuestaGraficoRepository respuestaGraficoRepository;
    private final ParteGraficoRepository parteGraficoRepository;

    public FormulaProcessor(RespuestaAbiertaRepository respuestaAbiertaRepository,
            PreguntaRepository preguntaRepository, RespuestaGraficoRepository respuestaGraficoRepository,ParteGraficoRepository parteGraficoRepository) {
        this.respuestaAbiertaRepository = respuestaAbiertaRepository;
        this.preguntaRepository = preguntaRepository;
        this.respuestaGraficoRepository = respuestaGraficoRepository;
        this.parteGraficoRepository = parteGraficoRepository;
    }

    private RespuestaAbierta obtenerValorPorIdAbierta(int id, int consulta) {
        return respuestaAbiertaRepository.findByConsultaIdAndPreguntaId(consulta, id);
    }

    private RespuestaGrafico obtenerValorPorId(int id, int consulta) {
        return respuestaGraficoRepository.findByConsultaIdAndParteGraficoId(consulta, id);
    }

    public Double procesarFormula(String formula, int consulta, String origen) {
        Matcher matcher = PATRON_ID.matcher(formula);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            if (origen.equals("abierta")) {
                RespuestaAbierta respuesta = obtenerValorPorIdAbierta(id, consulta);
                if (respuesta == null) {
                    Optional<Pregunta> pregunta = preguntaRepository.findById(id);
                    if (pregunta.isPresent()) {
                        throw new IllegalArgumentException(
                                "No se encontró respuesta. Por favor llenar primero la pregunta: " +
                                        pregunta.get().getTextoPregunta() + " en "
                                        + pregunta.get().getCategoria().getNombre());
                    } else {
                        throw new IllegalArgumentException("No se encontró la pregunta con ID " + id);
                    }
                }
                matcher.appendReplacement(sb, String.valueOf(Double.parseDouble(respuesta.getValor())));
            } else {
                RespuestaGrafico respuesta = obtenerValorPorId(id, consulta);
                if (respuesta == null) {
                    Optional<ParteGrafico> parteGrafico = parteGraficoRepository.findById(id);
                    if (parteGrafico.isPresent()) {
                        throw new IllegalArgumentException(
                                "No se encontró respuesta. Por favor llenar primero la pregunta: " +
                                        parteGrafico.get().getTexto() + " en "
                                        + parteGrafico.get().getSeccionGrafico().getNombre());
                    } else {
                        throw new IllegalArgumentException("No se encontró la pregunta con ID " + id);
                    }
                }
                matcher.appendReplacement(sb, String.valueOf(Double.parseDouble(respuesta.getValor())));
            }
        }
        matcher.appendTail(sb);
        String formulaEvaluable = sb.toString();
        Expression expression = new ExpressionBuilder(formulaEvaluable).build();
        return expression.evaluate();
    }
}