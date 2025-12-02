package com.edisonla.evaluacion_desempeno.dtos;

import java.util.Date;

public record RegisterRequest(String email, String password, String username, String legajo, String cuil, Date incorporacion) {
}
