package com.intellectus.backend.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.intellectus.backend.entities.Listado;
import org.springframework.stereotype.Repository;

@Repository
public interface ListadoRepository extends JpaRepository<Listado, Integer> {
}