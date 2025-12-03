package com.edisonla.evaluacion_desempeno.dtos;

import java.time.LocalDate;
import java.util.Date;

public record EvaluacionDto(
        Long id,
        String nombre,
        LocalDate fecha,
        String resultadoEscrito,
        String seniority,
        double resultadoCalculado,
        String disponibilidad,
        String puesto) {}
