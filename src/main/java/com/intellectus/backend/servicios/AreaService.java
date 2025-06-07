package com.intellectus.backend.servicios;

import org.springframework.stereotype.Service;
import com.intellectus.backend.dto.AreaDTO;
import com.intellectus.backend.repositorios.AreaRepository;
import java.util.List;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<AreaDTO> getAllAreas() {
        return areaRepository.findAll()
                .stream()
                .map(area -> new AreaDTO(area.getId(), area.getNombre()))
                .toList();
    }
}