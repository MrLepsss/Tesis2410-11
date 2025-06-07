package com.intellectus.backend.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.intellectus.backend.entities.TipoDeTranstorno;

@Repository
public interface TipoDeTranstornoRepository extends JpaRepository<TipoDeTranstorno, Integer> {
}