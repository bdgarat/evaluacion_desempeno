package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.dtos.ComportamientoDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import com.edisonla.evaluacion_desempeno.entities.Comportamiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompetenciaCualitativaMapper {
    @Mapping(target = "evaluacion", ignore = true)
    CompetenciaCualitativa toEntity(CompetenciaCualitativaDto dto);
    @Mapping(target = "evaluacion", ignore = true)
    CompetenciaCualitativaDto toDto(CompetenciaCualitativa entity);
}
