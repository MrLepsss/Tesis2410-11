package com.intellectus.backend.servicios;

import com.intellectus.backend.dto.RespuestaCerradaDTO;
import com.intellectus.backend.entities.Consulta;
import com.intellectus.backend.entities.OpcionListado;
import com.intellectus.backend.entities.Pregunta;
import com.intellectus.backend.entities.RespuestaCerrada;
import com.intellectus.backend.entities.id.Respuesta;
import com.intellectus.backend.repositorios.ConsultaRepository;
import com.intellectus.backend.repositorios.OpcionListadoRepository;
import com.intellectus.backend.repositorios.PreguntaRepository;
import com.intellectus.backend.repositorios.RespuestaCerradaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RespuestaCerradaService {

    private final RespuestaCerradaRepository respuestaCerradaRepository;
    private final PreguntaRepository preguntaRepository;
    private final ConsultaRepository consultaRepository;
    private final OpcionListadoRepository opcionListadoRepository;

    public RespuestaCerradaService(RespuestaCerradaRepository respuestaCerradaRepository,
            PreguntaRepository preguntaRepository,
            ConsultaRepository consultaRepository,
            OpcionListadoRepository opcionListadoRepository) {
        this.respuestaCerradaRepository = respuestaCerradaRepository;
        this.preguntaRepository = preguntaRepository;
        this.consultaRepository = consultaRepository;
        this.opcionListadoRepository = opcionListadoRepository;
    }

    public ResponseEntity<String> guardarRespuestasCerradas(List<RespuestaCerradaDTO> dtos) {
        List<RespuestaCerrada> respuestasParaGuardar = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        Set<Integer> idPreguntas = dtos.stream().map(RespuestaCerradaDTO::getIdPregunta).collect(Collectors.toSet());
        Set<Integer> idConsultas = dtos.stream().map(RespuestaCerradaDTO::getIdConsulta).collect(Collectors.toSet());
        Set<Integer> idOpciones = dtos.stream().map(RespuestaCerradaDTO::getIdOpcionListado)
                .collect(Collectors.toSet());

        Map<Integer, Pregunta> preguntasMap = preguntaRepository.findAllById(idPreguntas).stream()
                .collect(Collectors.toMap(Pregunta::getId, p -> p));
        Map<Integer, Consulta> consultasMap = consultaRepository.findAllById(idConsultas).stream()
                .collect(Collectors.toMap(Consulta::getId, c -> c));
        Map<Integer, OpcionListado> opcionesMap = opcionListadoRepository.findAllById(idOpciones).stream()
                .collect(Collectors.toMap(OpcionListado::getId, o -> o));

        List<RespuestaCerrada> existentes = respuestaCerradaRepository.findByConsultaIdInAndPreguntaIdIn(idConsultas,
                idPreguntas);
        Map<String, RespuestaCerrada> existenteMap = existentes.stream()
                .collect(Collectors.toMap(
                        r -> r.getConsulta().getId() + "-" + r.getPregunta().getId(),
                        r -> r));

        for (RespuestaCerradaDTO dto : dtos) {
            Pregunta pregunta = preguntasMap.get(dto.getIdPregunta());
            Consulta consulta = consultasMap.get(dto.getIdConsulta());
            OpcionListado opcion = opcionesMap.get(dto.getIdOpcionListado());

            if (pregunta == null || consulta == null || opcion == null) {
                errores.add("Consulta, pregunta u opción no encontrada. ID consulta: " + dto.getIdConsulta()
                        + ", ID pregunta: " + dto.getIdPregunta() + ", ID opción listado: " + dto.getIdOpcionListado());
                continue;
            }

            if (!pregunta.getPreguntaListado().getListado().getId().equals(opcion.getListado().getId())) {
                errores.add("La opción no pertenece al listado de la pregunta. ID pregunta: "
                        + dto.getIdPregunta() + ", ID opción: " + dto.getIdOpcionListado());
                continue;
            }

            String key = dto.getIdConsulta() + "-" + dto.getIdPregunta();
            RespuestaCerrada existente = existenteMap.get(key);

            if (existente == null) {
                RespuestaCerrada nueva = new RespuestaCerrada();
                nueva.setId(new Respuesta(dto.getIdConsulta(), dto.getIdPregunta()));
                nueva.setConsulta(consulta);
                nueva.setPregunta(pregunta);
                nueva.setOpcion(opcion);
                respuestasParaGuardar.add(nueva);
            } else {
                if (!existente.getOpcion().getId().equals(dto.getIdOpcionListado())) {
                    existente.setOpcion(opcion);
                    respuestasParaGuardar.add(existente);
                }
            }
        }

        if (!respuestasParaGuardar.isEmpty()) {
            respuestaCerradaRepository.saveAll(respuestasParaGuardar);
        }

        if (errores.isEmpty()) {
            return ResponseEntity.ok("Respuestas cerradas guardadas con éxito.");
        } else {
            return ResponseEntity.badRequest().body(String.join("\n", errores));
        }
    }
    public ResponseEntity<String> guardarRespuestaCerrada(RespuestaCerradaDTO dto) {
        Pregunta pregunta = preguntaRepository.findById(dto.getIdPregunta()).orElse(null);
        Consulta consulta = consultaRepository.findById(dto.getIdConsulta()).orElse(null);
        OpcionListado opcion = opcionListadoRepository.findById(dto.getIdOpcionListado()).orElse(null);
    
        if (pregunta == null || consulta == null || opcion == null) {
            return ResponseEntity.badRequest().body("Consulta, pregunta u opción no encontrada. ID consulta: "
                    + dto.getIdConsulta() + ", ID pregunta: " + dto.getIdPregunta()
                    + ", ID opción listado: " + dto.getIdOpcionListado());
        }
    
        if (!pregunta.getPreguntaListado().getListado().getId().equals(opcion.getListado().getId())) {
            return ResponseEntity.badRequest().body("La opción no pertenece al listado de la pregunta. ID pregunta: "
                    + dto.getIdPregunta() + ", ID opción: " + dto.getIdOpcionListado());
        }
    
        RespuestaCerrada existente = respuestaCerradaRepository
                .findByConsultaIdAndPreguntaId(dto.getIdConsulta(), dto.getIdPregunta());
    
        if (existente == null) {
            RespuestaCerrada nueva = new RespuestaCerrada();
            nueva.setId(new Respuesta(dto.getIdConsulta(), dto.getIdPregunta()));
            nueva.setConsulta(consulta);
            nueva.setPregunta(pregunta);
            nueva.setOpcion(opcion);
            respuestaCerradaRepository.save(nueva);
        } else {
            if (!existente.getOpcion().getId().equals(dto.getIdOpcionListado())) {
                existente.setOpcion(opcion);
                respuestaCerradaRepository.save(existente);
            }
        }
    
        return ResponseEntity.ok("Respuesta cerrada guardada con éxito.");
    }    
}