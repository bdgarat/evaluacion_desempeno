package com.edisonla.evaluacion_desempeno.dtos;

import java.util.Date;

public record EvaluacionRequest(String nombre,
                                String apellido,
                                Date incorporacion,
                                int legajo,
                                double resultadoFinal,
                                String mail,
                                boolean admin) {
}


