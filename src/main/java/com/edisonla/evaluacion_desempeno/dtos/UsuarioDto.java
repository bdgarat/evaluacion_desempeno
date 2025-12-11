package com.edisonla.evaluacion_desempeno.dtos;

import jakarta.persistence.Column;

import java.time.LocalDate;
import java.util.Date;

public record UsuarioDto(Long id,
                        String username,
                        String nombre,
                        String apellido,
                        String email,
                        String roles,
                        LocalDate incorporacion,
                        int legajo,
                        String cuil) {
}
