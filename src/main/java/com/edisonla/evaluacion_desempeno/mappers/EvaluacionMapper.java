package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.EvaluacionDto;
import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EvaluacionMapper {
    @Mapping(target = "evaluado", ignore = true)
    @Mapping(target = "evaluador", ignore = true)
    @Mapping(target = "validador", ignore = true)
    EvaluacionDto toDto(Evaluacion e);
    @Mapping(target = "evaluado", ignore = true)
    @Mapping(target = "evaluador", ignore = true)
    @Mapping(target = "validador", ignore = true)
    Evaluacion toEntity(EvaluacionDto dto);
}

