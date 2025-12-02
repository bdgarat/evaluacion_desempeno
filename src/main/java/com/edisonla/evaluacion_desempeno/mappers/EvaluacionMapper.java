package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.EvaluacionDto;
import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvaluacionMapper {
    EvaluacionDto toDto(Evaluacion e);
    Evaluacion toEntity(EvaluacionDto dto);
}

