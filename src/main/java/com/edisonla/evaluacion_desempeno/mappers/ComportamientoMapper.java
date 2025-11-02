package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.dtos.ComportamientoDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import com.edisonla.evaluacion_desempeno.entities.Comportamiento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComportamientoMapper {
    ComportamientoDto toDto(Comportamiento comportamiento);
    Comportamiento toEntity(ComportamientoDto dto);
}
