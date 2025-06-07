package com.intellectus.backend.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intellectus.backend.entities.Categoria;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByAreaId(Integer areaId);

    List<Categoria> findAllByVisibilidadTrue();

    List<Categoria> findByAreaIdAndVisibilidadTrue(Integer id);
}