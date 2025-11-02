package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetenciaCualitativaMapper {
    CompetenciaCualitativaDto toDto(CompetenciaCualitativa competenciaCualitativa);
    CompetenciaCualitativa toEntity(CompetenciaCualitativaDto dto);
}
