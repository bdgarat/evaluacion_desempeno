package com.edisonla.evaluacion_desempeno.dtos;

import com.edisonla.evaluacion_desempeno.enums.EstadoEvaluacion;

import java.time.LocalDate;

public record EvaluacionDto(
        Long id,
        String nombre,
        LocalDate fecha,
        String resultadoEscrito,
        String celula,
        double resultadoCalculado,
        String disponibilidad,
        String puesto,
        EstadoEvaluacion estado,
        Long evaluado,
        Long evaluador,
        Long validador) {}
