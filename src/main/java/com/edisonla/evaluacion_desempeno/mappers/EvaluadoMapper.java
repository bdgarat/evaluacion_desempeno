package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.dtos.EvaluadoDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import com.edisonla.evaluacion_desempeno.entities.Evaluado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvaluadoMapper {
    EvaluadoDto toDto(Evaluado evaluado);
    Evaluado toEntity(EvaluadoDto dto);
}
