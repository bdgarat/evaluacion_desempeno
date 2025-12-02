package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.EvaluacionRequest;
import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvaluacionRequestMapper {
    Evaluacion toEntity(EvaluacionRequest dto);
    EvaluacionRequest toDto(Evaluacion entity);
}
