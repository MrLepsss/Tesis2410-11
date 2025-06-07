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
import com.intellectus.backend.dto.MedicoDTO;
import com.intellectus.backend.entities.Consulta;
import com.intellectus.backend.entities.ConsultaMedico;
import com.intellectus.backend.entities.Medico;
import com.intellectus.backend.entities.id.ConsultaMedicoId;
import com.intellectus.backend.repositorios.ConsultaMedicoRepository;
import com.intellectus.backend.repositorios.ConsultaRepository;
import com.intellectus.backend.repositorios.MedicoRepository;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;
    private final ConsultaMedicoRepository consultaMedicoRepository;

    public MedicoService(MedicoRepository medicoRepository, ConsultaMedicoRepository consultaMedicoRepository,
            ConsultaRepository consultaRepository) {
        this.medicoRepository = medicoRepository;
        this.consultaMedicoRepository = consultaMedicoRepository;
        this.consultaRepository = consultaRepository;
    }

    public List<MedicoDTO> obtenerTodos() {
        List<MedicoDTO> medicosDTO = new ArrayList<>();
        List<Medico> medicos = medicoRepository.findAll();
        for (Medico medico : medicos) {
            medicosDTO.add(new MedicoDTO(medico.getId(), medico.getNombre(), medico.getEspecialidad()));
        }
        return medicosDTO;
    }

    public ResponseEntity<String> sincronizarMedicosConsulta(ConsultaLoteDTO dto) {
        Optional<Consulta> consultaOpt = consultaRepository.findById(dto.getConsultaId());
        if (!consultaOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Consulta no encontrada con ID: " + dto.getConsultaId());
        }

        List<Medico> medicos = medicoRepository.findAllById(dto.getIds());
        if (medicos.size() != dto.getIds().size()) {
            return ResponseEntity.badRequest().body("Uno o más médicos no existen en la base de datos.");
        }

        List<ConsultaMedico> actuales = consultaMedicoRepository.findByConsultaId(dto.getConsultaId());
        Set<Integer> actualesIds = actuales.stream()
                .map(cm -> cm.getMedico().getId())
                .collect(Collectors.toSet());

        Set<Integer> nuevosIds = new HashSet<>(dto.getIds());

        List<ConsultaMedico> aEliminar = actuales.stream()
                .filter(cm -> !nuevosIds.contains(cm.getMedico().getId()))
                .toList();

        List<ConsultaMedico> aAgregar = nuevosIds.stream()
                .filter(id -> !actualesIds.contains(id))
                .map(id -> {
                    ConsultaMedico cm = new ConsultaMedico();
                    cm.setId(new ConsultaMedicoId(dto.getConsultaId(), id));
                    cm.setConsulta(consultaOpt.get());
                    cm.setMedico(medicos.stream().filter(m -> m.getId().equals(id)).findFirst().get());
                    return cm;
                })
                .toList();

        if (!aEliminar.isEmpty()) {
            consultaMedicoRepository.deleteAll(aEliminar);
        }

        if (!aAgregar.isEmpty()) {
            consultaMedicoRepository.saveAll(aAgregar);
        }

        return ResponseEntity.ok("Relación médico-consulta sincronizada con éxito.");
    }

    public List<MedicoDTO> obtenerMedicoConsulta(Integer consultaId) {
        List<MedicoDTO> medicosDTO = new ArrayList<>();
        List<ConsultaMedico> actuales = consultaMedicoRepository.findByConsultaId(consultaId);
        for (ConsultaMedico consultaMedico : actuales) {
            Medico medico = consultaMedico.getMedico();
            medicosDTO.add(new MedicoDTO(medico.getId(), medico.getNombre(), medico.getEspecialidad()));
        }
        return medicosDTO;
    }
}