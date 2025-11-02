package com.edisonla.evaluacion_desempeno.mappers;


import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCuantitativa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetenciaCuantitativaMapper {
    CompetenciaCuantitativaDto toDto(CompetenciaCuantitativa competenciaCuantitativa);
    CompetenciaCuantitativa toEntity(CompetenciaCuantitativaDto dto);
}
