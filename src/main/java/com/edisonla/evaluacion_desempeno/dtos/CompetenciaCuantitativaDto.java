package com.edisonla.evaluacion_desempeno.dtos;

import java.util.Date;

public record CompetenciaCuantitativaDto(Long id, String nombre, String descripcion, Date fecha, double resultado,
                                         boolean validado, Long evaluacion) {
}
