package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCuantitativa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompetenciaCuantitativaMapper {
    @Mapping(target = "evaluacion", ignore = true)
    CompetenciaCuantitativa toEntity(CompetenciaCuantitativaDto dto);
    @Mapping(target = "evaluacion", ignore = true)
    CompetenciaCuantitativaDto toDto(CompetenciaCuantitativa entity);
}
