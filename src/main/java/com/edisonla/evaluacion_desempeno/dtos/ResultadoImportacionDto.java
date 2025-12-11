package com.edisonla.evaluacion_desempeno.dtos;

import com.edisonla.evaluacion_desempeno.entities.Usuario;

import java.util.List;

public record ResultadoImportacionDto(
        int total,
        int creados,
        int actualizados,
        int errores,
        List<String> mensajeError,
        List<Usuario> usuariosCargados
) {}
