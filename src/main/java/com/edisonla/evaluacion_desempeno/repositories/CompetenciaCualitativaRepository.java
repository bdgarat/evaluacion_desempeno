package com.edisonla.evaluacion_desempeno.repositories;

import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetenciaCualitativaRepository extends JpaRepository<CompetenciaCualitativa, Long> {
    List<CompetenciaCualitativa> findAllByEvaluacionId(Long evaluacionId);
}
