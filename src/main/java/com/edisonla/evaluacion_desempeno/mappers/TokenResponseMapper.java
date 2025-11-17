package com.edisonla.evaluacion_desempeno.mappers;

import com.edisonla.evaluacion_desempeno.dtos.TokenResponse;
import com.edisonla.evaluacion_desempeno.entities.Token;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenResponseMapper {
    Token toEntity(TokenResponse dto);
    TokenResponse toDto(Token entity);
}
