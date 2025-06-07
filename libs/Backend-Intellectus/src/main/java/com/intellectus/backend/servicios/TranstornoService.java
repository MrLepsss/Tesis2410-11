package com.intellectus.backend.servicios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.intellectus.backend.dto.ConsultaLoteDTO;
import com.intellectus.backend.dto.SeleccionTrastornoDTO;
import com.intellectus.backend.dto.TranstornoEspecificoDTO;
import com.intellectus.backend.dto.TranstornosDTO;
import com.intellectus.backend.entities.Consulta;
import com.intellectus.backend.entities.TipoDeTranstorno;
import com.intellectus.backend.entities.TranstornoEspecifico;
import com.intellectus.backend.entities.TranstornoEspecificoConsulta;
import com.intellectus.backend.entities.TranstornoGeneral;
import com.intellectus.backend.entities.id.TranstornoEspecificoConsultaId;
import com.intellectus.backend.repositorios.ConsultaRepository;
import com.intellectus.backend.repositorios.TipoDeTranstornoRepository;
import com.intellectus.backend.repositorios.TranstornoEspecificoConsultaRepository;
import com.intellectus.backend.repositorios.TranstornoEspecificoRepository;
import com.intellectus.backend.repositorios.TranstornoGeneralRepository;

@Service
public class TranstornoService {

    private final TipoDeTranstornoRepository tipoDeTranstornoRepository;
    private final TranstornoEspecificoConsultaRepository transtornoEspecificoConsultaRepository;
    private final TranstornoEspecificoRepository transtornoEspecificoRepository;
    private final TranstornoGeneralRepository transtornoGeneralRepository;
    private final ConsultaRepository consultaRepository;

    public TranstornoService(TipoDeTranstornoRepository tipoDeTranstornoRepository,
            TranstornoEspecificoConsultaRepository transtornoEspecificoConsultaRepository,
            TranstornoEspecificoRepository transtornoEspecificoRepository,
            TranstornoGeneralRepository transtornoGeneralRepository, ConsultaRepository consultaRepository) {
        this.tipoDeTranstornoRepository = tipoDeTranstornoRepository;
        this.transtornoEspecificoConsultaRepository = transtornoEspecificoConsultaRepository;
        this.transtornoEspecificoRepository = transtornoEspecificoRepository;
        this.transtornoGeneralRepository = transtornoGeneralRepository;
        this.consultaRepository = consultaRepository;
    }

    public List<TranstornosDTO> obtenerTranstornos() {
        List<TranstornosDTO> transtornosDTOs = new ArrayList<>();
        List<TipoDeTranstorno> transtornos = tipoDeTranstornoRepository.findAll();
        for (TipoDeTranstorno tipoDeTranstorno : transtornos) {
            transtornosDTOs.add(new TranstornosDTO(tipoDeTranstorno.getId(), tipoDeTranstorno.getNombre()));
        }
        return transtornosDTOs;
    }

    public List<TranstornosDTO> obtenerTranstornosGeneral(Integer transtornoId) {
        List<TranstornosDTO> transtornosDTOs = new ArrayList<>();
        List<TranstornoGeneral> transtornos = transtornoGeneralRepository.findByTipoDeTranstornoId(transtornoId);
        for (TranstornoGeneral transtornoGeneral : transtornos) {
            transtornosDTOs.add(new TranstornosDTO(transtornoGeneral.getId(), transtornoGeneral.getNombre()));
        }
        return transtornosDTOs;
    }

    public List<TranstornoEspecificoDTO>  obtenerTranstornosEspecificos(Integer transtornoGeneralId) {
        List<TranstornoEspecificoDTO> transtornosDTOs = new ArrayList<>();
        List<TranstornoEspecifico> transtornos = transtornoEspecificoRepository
                .findByTranstornoGeneralId(transtornoGeneralId);
        for (TranstornoEspecifico transtornoGeneral : transtornos) {
            transtornosDTOs.add(new TranstornoEspecificoDTO(transtornoGeneral.getId(), transtornoGeneral.getDsmV(),
                    transtornoGeneral.getCie10(), transtornoGeneral.getNombre()));
        }
        return transtornosDTOs;
    }

    public ResponseEntity<String> sincronizarTranstornoEspecificoConsulta(ConsultaLoteDTO dto) {
        Optional<Consulta> consultaOpt = consultaRepository.findById(dto.getConsultaId());
        if (!consultaOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Consulta no encontrada con ID: " + dto.getConsultaId());
        }

        List<TranstornoEspecifico> transtornoEspecificos = transtornoEspecificoRepository.findAllById(dto.getIds());
        if (transtornoEspecificos.size() != dto.getIds().size()) {
            return ResponseEntity.badRequest().body("Uno o más transtornos especificos no existen en la base de datos.");
        }

        List<TranstornoEspecificoConsulta> actuales = transtornoEspecificoConsultaRepository.findByConsultaId(dto.getConsultaId());
        Set<Integer> actualesIds = actuales.stream()
                .map(tec -> tec.getTranstornoEspecifico().getId())
                .collect(Collectors.toSet());

        Set<Integer> nuevosIds = new HashSet<>(dto.getIds());

        List<TranstornoEspecificoConsulta> aEliminar = actuales.stream()
                .filter(tec -> !nuevosIds.contains(tec.getTranstornoEspecifico().getId()))
                .toList();

        List<TranstornoEspecificoConsulta> aAgregar = nuevosIds.stream()
                .filter(id -> !actualesIds.contains(id))
                .map(id -> {
                    TranstornoEspecificoConsulta tec = new TranstornoEspecificoConsulta();
                    tec.setId(new TranstornoEspecificoConsultaId(dto.getConsultaId(), id));
                    tec.setConsulta(consultaOpt.get());
                    tec.setTranstornoEspecifico(transtornoEspecificos.stream().filter(m -> m.getId().equals(id)).findFirst().get());
                    return tec;
                })
                .toList();

        if (!aEliminar.isEmpty()) {
            transtornoEspecificoConsultaRepository.deleteAll(aEliminar);
        }

        if (!aAgregar.isEmpty()) {
            transtornoEspecificoConsultaRepository.saveAll(aAgregar);
        }

        return ResponseEntity.ok("Relación transtorno-consulta sincronizada con éxito.");
    }

    public List<SeleccionTrastornoDTO> obtenerSeleccionTranstorno(Integer consultaId) {

        List<SeleccionTrastornoDTO> transtornosDTOs = new ArrayList<>();
        List<TranstornoEspecificoConsulta> transtornos = transtornoEspecificoConsultaRepository
                .findByConsultaId(consultaId);
        for (TranstornoEspecificoConsulta transtornoEspecificoConsulta : transtornos) {
            transtornosDTOs.add(new SeleccionTrastornoDTO(
                    transtornoEspecificoConsulta.getTranstornoEspecifico().getTranstornoGeneral().getTipoDeTranstorno()
                            .getId(),
                    transtornoEspecificoConsulta.getTranstornoEspecifico().getTranstornoGeneral().getId(),
                    transtornoEspecificoConsulta.getTranstornoEspecifico().getId()));
        }
        return transtornosDTOs;
    }
}
