package com.edisonla.evaluacion_desempeno.mappers;


import com.edisonla.evaluacion_desempeno.dtos.UsuarioDto;
import com.edisonla.evaluacion_desempeno.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDto toDto(Usuario e);
    Usuario toEntity(UsuarioDto dto);
}
