package com.edisonla.evaluacion_desempeno.dtos;

import java.util.Date;

public record EvaluacionDto(
        Long id,
        String dni,
        String cuil,
        String nombre,
        String apellido,
        Date incorporacion,
        int legajo,
        String seniority,
        String disponibilidad,
        String mail,
        String celula,
        String equipoMetodologico,
        String nuevoPuesto,
        String puesto,
        double resultadoFinal,
        boolean esAdmin
) {}
