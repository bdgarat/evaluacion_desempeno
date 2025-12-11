package com.edisonla.evaluacion_desempeno.dtos;

import java.time.LocalDate;


    public record NominaUsuarioDto(
            int legajo,
            String cuil,
            String nombre,
            String apellido,
            String email,
            LocalDate fechaInco
    ) {}

