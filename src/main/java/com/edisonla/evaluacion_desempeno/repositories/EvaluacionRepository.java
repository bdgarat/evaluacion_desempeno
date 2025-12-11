package com.edisonla.evaluacion_desempeno.repositories;

import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import com.edisonla.evaluacion_desempeno.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

    List<Evaluacion> findAllByEvaluadorId(Long id);
    List<Evaluacion> findAllByValidadorId(Long id);
}
