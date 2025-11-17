package com.edisonla.evaluacion_desempeno.repositories;

import com.edisonla.evaluacion_desempeno.entities.Evaluado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EvaluadoRepository extends JpaRepository<Evaluado, Long> {
    List<Evaluado> findAllByIncorporacion(Date fecha);
    Optional<Evaluado> findByLegajo(int legajo);
    Optional<Evaluado> findByNombre(String nombre);
    Optional<Evaluado> findByApellido(String apellido);
    Optional<Evaluado> findByMail(String mail);
}
