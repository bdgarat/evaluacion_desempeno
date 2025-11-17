package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.LoginRequest;
import com.edisonla.evaluacion_desempeno.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginRequestMapper {
    LoginRequest toDto(Usuario usuario);
    Usuario toEntity(LoginRequest loginRequest);
}
