package com.intellectus.backend.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intellectus.backend.entities.OpcionListado;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionListadoRepository extends JpaRepository<OpcionListado, Integer> {
    List<OpcionListado> findByListadoId(Integer listadoId);

    List<OpcionListado> findByListadoIdIn(List<Integer> ids);
}