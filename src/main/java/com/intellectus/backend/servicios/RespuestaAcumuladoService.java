package com.intellectus.backend.servicios;

import com.intellectus.backend.dto.PreguntaDTO;
import com.intellectus.backend.dto.PreguntaValorFijoDTO;
import com.intellectus.backend.entities.Categoria;
import com.intellectus.backend.entities.Consulta;
import com.intellectus.backend.entities.OpcionListado;
import com.intellectus.backend.entities.Pregunta;
import com.intellectus.backend.entities.RespuestaAbierta;
import com.intellectus.backend.entities.RespuestaAcumulado;
import com.intellectus.backend.entities.RespuestaCerrada;
import com.intellectus.backend.entities.id.AcumuladoCategoriaId;
import com.intellectus.backend.repositorios.PreguntaRepository;
import com.intellectus.backend.repositorios.RespuestaAbiertaRepository;
import com.intellectus.backend.repositorios.RespuestaAcumuladoRepository;
import com.intellectus.backend.repositorios.RespuestaCerradaRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RespuestaAcumuladoService {

    private final RespuestaAcumuladoRepository respuestaAcumuladoRepository;
    private final RespuestaCerradaRepository respuestaCerradaRepository;
    private final RespuestaAbiertaRepository respuestaAbiertaRepository;
    private final PreguntaRepository preguntaRepository;
    private final PreguntaService preguntaService;

    public RespuestaAcumuladoService(RespuestaAcumuladoRepository respuestaAcumuladoRepository,
            RespuestaCerradaRepository respuestaCerradaRepository, PreguntaRepository preguntaRepository,
            PreguntaService preguntaService,
            RespuestaAbiertaRepository respuestaAbiertaRepository) {
        this.respuestaAcumuladoRepository = respuestaAcumuladoRepository;
        this.respuestaCerradaRepository = respuestaCerradaRepository;
        this.preguntaRepository = preguntaRepository;
        this.preguntaService = preguntaService;
        this.respuestaAbiertaRepository = respuestaAbiertaRepository;
    }

    public ResponseEntity<String> calcularSuma(Categoria categoria, Consulta consulta) {

        List<Pregunta> preguntas = preguntaRepository.findByCategoriaIdConCategoria(categoria.getId());

        Set<Integer> idPreguntasReferencia = preguntas.stream()
                .filter(p -> "Desplegable".equals(p.getTipoRespuesta()))
                .map(Pregunta::getId)
                .collect(Collectors.toSet());

        Set<Pregunta> preguntasReferencia = preguntas.stream()
                .filter(p -> "Referencia".equals(p.getTipoRespuesta()))
                .collect(Collectors.toSet());

        Set<Integer> idPreguntasTexto = preguntas.stream()
                .filter(p -> "Texto".equals(p.getTipoRespuesta()))
                .map(Pregunta::getId)
                .collect(Collectors.toSet());

        Set<Integer> idConsultas = Set.of(consulta.getId());
        List<RespuestaCerrada> respuestasCerradas = respuestaCerradaRepository
                .findByConsultaIdInAndPreguntaIdIn(idConsultas, idPreguntasReferencia);
        String acumuladoTexto = "";
        Integer acumuladoNumero = 0;
        boolean almenosUna=false;
        for (RespuestaCerrada respuestaCerrada : respuestasCerradas) {
            if ("Numero".equals(respuestaCerrada.getOpcion().getTipoValor())) {
                acumuladoNumero += Integer.parseInt(respuestaCerrada.getOpcion().getValor());
                almenosUna=true;
            } else if ("Texto".equals(respuestaCerrada.getOpcion().getTipoValor())) {
                acumuladoTexto += respuestaCerrada.getOpcion().getValor() + " ";
                almenosUna=true;
            }
        }
        for (Pregunta pregunta : preguntasReferencia) {
            PreguntaDTO respuesta = preguntaService.procesarPreguntaConReferencia(pregunta, consulta.getId());
            PreguntaValorFijoDTO preguntaValorFijo = (PreguntaValorFijoDTO) respuesta;
            if (preguntaValorFijo.getTipoRespuesta().equals("Error")) {
                return ResponseEntity.badRequest()
                        .body("Error en la pregunta con formula: " + preguntaValorFijo.getValor());
            }
            acumuladoNumero += Integer.parseInt(preguntaValorFijo.getValor());
        }
        for (Integer idPregunta : idPreguntasTexto) {
            RespuestaAbierta respuestaAbierta = respuestaAbiertaRepository
                    .findByConsultaIdAndPreguntaId(consulta.getId(), idPregunta);
            if (respuestaAbierta != null) {
                acumuladoTexto += respuestaAbierta.getValor() + " , ";
                almenosUna=true;
            }
        }
        AcumuladoCategoriaId acumuladoCategoriaId = new AcumuladoCategoriaId(categoria.getId(), consulta.getId());
        Optional<RespuestaAcumulado> respuestaAcumuladoOpt = respuestaAcumuladoRepository
                .findById(acumuladoCategoriaId);
        RespuestaAcumulado respuestaAcumulado;
        if (respuestaAcumuladoOpt.isPresent()) {
            respuestaAcumulado = respuestaAcumuladoOpt.get();
        } else {
            respuestaAcumulado = new RespuestaAcumulado();
            respuestaAcumulado.setId(acumuladoCategoriaId);
            respuestaAcumulado.setConsulta(consulta);
            respuestaAcumulado.setCategoria(categoria);
        }
        if( !almenosUna) {
            return ResponseEntity.ok("No se encontraron respuestas para la categoría: " + categoria.getNombre());
        }
        if (!acumuladoTexto.isEmpty()) {
            respuestaAcumulado.setValor(acumuladoTexto.trim());
            respuestaAcumuladoRepository.save(respuestaAcumulado);
            return ResponseEntity.ok("Acumulado de texto guardado: " + acumuladoTexto.trim());
        } else {
            respuestaAcumulado.setValor(String.valueOf(acumuladoNumero));
            respuestaAcumuladoRepository.save(respuestaAcumulado);
            return ResponseEntity.ok("Acumulado de número guardado: " + acumuladoNumero);
        }
    }

    public ResponseEntity<String> contar(Categoria categoria, Consulta consulta) {
        List<Pregunta> preguntas = preguntaRepository.findByCategoriaIdConCategoria(categoria.getId());
        if (preguntas.isEmpty())
            return ResponseEntity.ok("No hay preguntas para la categoría: " + categoria.getNombre());

        List<OpcionListado> opciones = preguntas.get(0).getPreguntaListado().getListado().getOpciones();

        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (OpcionListado opcionObj : opciones) {
            String valor = opcionObj.getTexto();
            conteo.put(valor, 0);
        }

        Set<Integer> idPreguntas = preguntas.stream()
                .map(Pregunta::getId)
                .collect(Collectors.toSet());
        Set<Integer> idConsultas = Set.of(consulta.getId());

        List<RespuestaCerrada> respuestasCerradas = respuestaCerradaRepository
                .findByConsultaIdInAndPreguntaIdIn(idConsultas, idPreguntas);

        for (RespuestaCerrada respuesta : respuestasCerradas) {
            String valor = respuesta.getOpcion().getTexto();
            conteo.computeIfPresent(valor, (k, v) -> v + 1);
        }

        String resultado = conteo.values().stream()
                .map(String::valueOf)
                .collect(Collectors.joining("/"));
        AcumuladoCategoriaId acumuladoCategoriaId = new AcumuladoCategoriaId(categoria.getId(), consulta.getId());
        Optional<RespuestaAcumulado> respuestaAcumuladoOpt = respuestaAcumuladoRepository
                .findById(acumuladoCategoriaId);
        RespuestaAcumulado respuestaAcumulado;
        if (respuestaAcumuladoOpt.isPresent()) {
            respuestaAcumulado = respuestaAcumuladoOpt.get();
        } else {
            respuestaAcumulado = new RespuestaAcumulado();
            respuestaAcumulado.setId(acumuladoCategoriaId);
            respuestaAcumulado.setConsulta(consulta);
            respuestaAcumulado.setCategoria(categoria);
        }
        respuestaAcumulado.setValor(resultado);
        respuestaAcumuladoRepository.save(respuestaAcumulado);
        return ResponseEntity.ok("Conteo de respuestas acumuladas guardado: " + resultado);
    }

    public ResponseEntity<String> sumarIndividual(Categoria categoria, Consulta consulta) {
        List<Pregunta> preguntas = preguntaRepository.findByCategoriaIdConCategoria(categoria.getId());
        if (preguntas.isEmpty())
            return ResponseEntity.ok("No hay preguntas para la categoría: " + categoria.getNombre());

        Map<String, Integer> acumulados = new LinkedHashMap<>();
        Map<Integer, String> idPreguntaClave = preguntas.stream()
                .collect(Collectors.toMap(
                        Pregunta::getId,
                        p -> {
                            String texto = p.getTextoPregunta();
                            int idx = texto.indexOf(':');
                            return idx > 0 ? texto.substring(0, idx).trim() : texto.trim();
                        }));

        Set<Integer> idPreguntas = idPreguntaClave.keySet();
        Set<Integer> idConsultas = Set.of(consulta.getId());

        List<RespuestaCerrada> respuestasCerradas = respuestaCerradaRepository
                .findByConsultaIdInAndPreguntaIdIn(idConsultas, idPreguntas);
        if (respuestasCerradas.isEmpty()) {
            return ResponseEntity.ok("No hay respuestas cerradas para la categoría: " + categoria.getNombre());
        }

        for (RespuestaCerrada respuesta : respuestasCerradas) {
            Integer idPregunta = respuesta.getPregunta().getId();
            String clave = idPreguntaClave.get(idPregunta);
            int valor = 0;
            try {
                valor = Integer.parseInt(respuesta.getOpcion().getValor());
            } catch (NumberFormatException ex) {
                System.err.println("Valor no numérico para la opción: " + respuesta.getOpcion().getValor());
            }
            acumulados.put(clave, acumulados.getOrDefault(clave, 0) + valor);
        }

        String resultado = acumulados.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("\n"));

        AcumuladoCategoriaId acumuladoCategoriaId = new AcumuladoCategoriaId(categoria.getId(), consulta.getId());
        RespuestaAcumulado respuestaAcumulado = respuestaAcumuladoRepository.findById(acumuladoCategoriaId)
                .orElseGet(() -> {
                    RespuestaAcumulado nuevo = new RespuestaAcumulado();
                    nuevo.setId(acumuladoCategoriaId);
                    nuevo.setConsulta(consulta);
                    nuevo.setCategoria(categoria);
                    return nuevo;
                });
        respuestaAcumulado.setValor(resultado);
        respuestaAcumuladoRepository.save(respuestaAcumulado);

        return ResponseEntity.ok("Acumulado individual guardado:\n" + resultado);
    }
}