package com.intellectus.backend.servicios;

import java.util.List;
import org.springframework.stereotype.Service;
import com.intellectus.backend.dto.CategoriaDTO;
import com.intellectus.backend.entities.Categoria;
import com.intellectus.backend.repositorios.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> getCategoriasByAreaId(Integer areaId) {
    return categoriaRepository.findByAreaId(areaId)
            .stream()
            .filter(Categoria::isVisibilidad)
            .map(categoria -> new CategoriaDTO(
                    categoria.getId(),
                    categoria.getNombre(),
                    categoria.getTipoResultado()))
            .toList();
}

}