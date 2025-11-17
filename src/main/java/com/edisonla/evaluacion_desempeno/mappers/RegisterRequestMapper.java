package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.RegisterRequest;
import com.edisonla.evaluacion_desempeno.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterRequestMapper {
    RegisterRequest toDto(Usuario usuario);
    Usuario toEntity(RegisterRequest registerRequest);
}
