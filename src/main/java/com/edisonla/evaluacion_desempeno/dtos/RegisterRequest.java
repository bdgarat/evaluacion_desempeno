package com.edisonla.evaluacion_desempeno.dtos;

import java.time.LocalDate;

public record RegisterRequest(String email, String password, String username, String legajo, String cuil, LocalDate incorporacion, String nombre, String apellido) {
}
