package com.edisonla.evaluacion_desempeno.repositories;


import com.edisonla.evaluacion_desempeno.entities.Comportamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComportamientoRepository extends JpaRepository<Comportamiento, Long> {
    List<Comportamiento> findAllByCompetenciaCuantitativaId(Long ccId);
}
