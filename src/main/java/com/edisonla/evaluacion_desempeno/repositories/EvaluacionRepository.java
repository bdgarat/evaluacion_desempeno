package com.edisonla.evaluacion_desempeno.repositories;

import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

}
