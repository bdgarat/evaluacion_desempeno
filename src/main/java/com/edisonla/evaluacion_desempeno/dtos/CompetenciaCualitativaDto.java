package com.edisonla.evaluacion_desempeno.dtos;

import java.time.LocalDate;

public record CompetenciaCualitativaDto(Long id, String descripcion, String devolucion, String pregunta, boolean validado,
                                        Long evaluacion) {
}
