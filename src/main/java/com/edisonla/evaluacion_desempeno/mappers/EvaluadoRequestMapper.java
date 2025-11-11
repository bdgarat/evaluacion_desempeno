package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.EvaluadoRequest;
import com.edisonla.evaluacion_desempeno.entities.Evaluado;

public class EvaluadoRequestMapper {
    private EvaluadoRequestMapper() {
        throw new UnsupportedOperationException("This class should never be instantiated");
    }

    public static Evaluado toEntity(final EvaluadoRequest dto) {
        return new Evaluado(dto.nombre(),
                dto.apellido(),
                dto.incorporacion(),
                dto.legajo(),
                dto.resultadoFinal(),
                dto.mail());
    }

    public static EvaluadoRequest toDto(final Evaluado entity) {
        return new EvaluadoRequest(
                entity.getNombre(),
                entity.getApellido(),
                entity.getIncorporacion(),
                entity.getLegajo(),
                entity.getResultadoFinal(),
                entity.getMail(),
                entity.isAdmin()
        );
    }
    //String nombre, String apellido, LocalDateTime incorporacion, int legajo, String username, String mail
}
