package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.ComportamientoDto;
import com.edisonla.evaluacion_desempeno.entities.Comportamiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComportamientoMapper {
    @Mapping(target = "competenciaCuantitativa", ignore = true)
    Comportamiento toEntity(ComportamientoDto dto);
    @Mapping(target = "competenciaCuantitativa", ignore = true)
    ComportamientoDto toDto(Comportamiento entity);
}

