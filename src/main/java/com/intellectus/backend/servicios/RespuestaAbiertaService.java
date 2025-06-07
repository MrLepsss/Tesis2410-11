package com.intellectus.backend.servicios;

import com.intellectus.backend.dto.RespuestaAbiertaDTO;
import com.intellectus.backend.entities.Consulta;
import com.intellectus.backend.entities.Pregunta;
import com.intellectus.backend.entities.RespuestaAbierta;
import com.intellectus.backend.entities.id.Respuesta;
import com.intellectus.backend.repositorios.ConsultaRepository;
import com.intellectus.backend.repositorios.PreguntaRepository;
import com.intellectus.backend.repositorios.RespuestaAbiertaRepository;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaAbiertaService {

    private final RespuestaAbiertaRepository respuestaAbiertaRepository;
    private final PreguntaRepository preguntaRepository;
    private final ConsultaRepository consultaRepository;

    public RespuestaAbiertaService(RespuestaAbiertaRepository respuestaAbiertaRepository,
            PreguntaRepository preguntaRepository,
            ConsultaRepository consultaRepository) {
        this.respuestaAbiertaRepository = respuestaAbiertaRepository;
        this.preguntaRepository = preguntaRepository;
        this.consultaRepository = consultaRepository;
    }

    public ResponseEntity<String> guardarRespuestasAbiertas(List<RespuestaAbiertaDTO> dtos) {
        List<RespuestaAbierta> respuestasParaGuardar = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        Set<Integer> idPreguntas = dtos.stream().map(RespuestaAbiertaDTO::getIdPregunta).collect(Collectors.toSet());
        Set<Integer> idConsultas = dtos.stream().map(RespuestaAbiertaDTO::getIdConsulta).collect(Collectors.toSet());

        Map<Integer, Pregunta> preguntasMap = preguntaRepository.findAllById(idPreguntas).stream()
                .collect(Collectors.toMap(Pregunta::getId, p -> p));
        Map<Integer, Consulta> consultasMap = consultaRepository.findAllById(idConsultas).stream()
                .collect(Collectors.toMap(Consulta::getId, c -> c));

        List<RespuestaAbierta> respuestasExistentes = respuestaAbiertaRepository
                .findByConsultaIdInAndPreguntaIdIn(idConsultas, idPreguntas);
        Map<String, RespuestaAbierta> respuestaExistenteMap = respuestasExistentes.stream()
                .collect(Collectors.toMap(
                        r -> r.getConsulta().getId() + "-" + r.getPregunta().getId(),
                        r -> r));

        for (RespuestaAbiertaDTO dto : dtos) {
            Pregunta pregunta = preguntasMap.get(dto.getIdPregunta());
            Consulta consulta = consultasMap.get(dto.getIdConsulta());

            if (pregunta == null || consulta == null) {
                errores.add("Consulta o pregunta no encontrada. ID consulta: "
                        + dto.getIdConsulta() + ", ID pregunta: " + dto.getIdPregunta());
                continue;
            }

            String key = dto.getIdConsulta() + "-" + dto.getIdPregunta();
            RespuestaAbierta existente = respuestaExistenteMap.get(key);

            if (existente == null) {
                RespuestaAbierta nueva = new RespuestaAbierta();
                nueva.setId(new Respuesta(dto.getIdConsulta(), dto.getIdPregunta()));
                nueva.setConsulta(consulta);
                nueva.setPregunta(pregunta);
                nueva.setValor(dto.getValor());
                respuestasParaGuardar.add(nueva);
            } else {
                if (existente.getValor() == null || !existente.getValor().equals(dto.getValor())) {
                    existente.setValor(dto.getValor());
                    respuestasParaGuardar.add(existente);
                }
            }
        }

        if (!respuestasParaGuardar.isEmpty()) {
            respuestaAbiertaRepository.saveAll(respuestasParaGuardar);
        }

        if (errores.isEmpty()) {
            return ResponseEntity.ok("Respuestas abiertas guardadas con éxito.");
        } else {
            return ResponseEntity.badRequest().body(String.join("\n", errores));
        }
    }

    public ResponseEntity<String> guardarRespuestaAbierta(RespuestaAbiertaDTO dto) {
        Pregunta pregunta = preguntaRepository.findById(dto.getIdPregunta()).orElse(null);
        Consulta consulta = consultaRepository.findById(dto.getIdConsulta()).orElse(null);
    
        if (pregunta == null || consulta == null) {
            return ResponseEntity.badRequest().body("Consulta o pregunta no encontrada. ID consulta: "
                    + dto.getIdConsulta() + ", ID pregunta: " + dto.getIdPregunta());
        }
    
        RespuestaAbierta existente = respuestaAbiertaRepository
                .findByConsultaIdAndPreguntaId(dto.getIdConsulta(), dto.getIdPregunta());
    
        if (existente == null) {
            RespuestaAbierta nueva = new RespuestaAbierta();
            nueva.setId(new Respuesta(dto.getIdConsulta(), dto.getIdPregunta()));
            nueva.setConsulta(consulta);
            nueva.setPregunta(pregunta);
            nueva.setValor(dto.getValor());
            respuestaAbiertaRepository.save(nueva);
        } else {
            if (existente.getValor() == null || !existente.getValor().equals(dto.getValor())) {
                existente.setValor(dto.getValor());
                respuestaAbiertaRepository.save(existente);
            }
        }
    
        return ResponseEntity.ok("Respuesta abierta guardada con éxito.");
    }
}