package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.UsuarioDto;
import com.edisonla.evaluacion_desempeno.entities.Usuario;
import com.edisonla.evaluacion_desempeno.mappers.UsuarioMapper;
import com.edisonla.evaluacion_desempeno.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.edisonla.evaluacion_desempeno.enums.Roles;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;

    public Iterable<UsuarioDto> getAll(String token) {
        Usuario me = repository.findByEmail(jwtService.extractEmail(token))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
        if(me.getRoles() != null && me.getRoles().contains(Roles.ADMIN.toString())) {
            List<Usuario> usuarios = repository.findAll();
            return usuarios
                    .stream()
                    .map(usuarioMapper::toDto) //method reference reemplazo de (e -> mapper.toDto(e))
                    .toList();
        } else {
            throw new RuntimeException("No tiene permisos para realizar esta accion");
        }
    }

    public UsuarioDto whoAmI(String token) {
        Usuario me = repository.findByEmail(jwtService.extractEmail(token))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
        return usuarioMapper.toDto(me);
    }

    public UsuarioDto updateMyself(String token, UsuarioDto dto) {
        Usuario me = repository.findByEmail(jwtService.extractEmail(token))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
        Usuario updated = usuarioMapper.toEntity(dto);
        updated.setId(me.getId());
        Usuario res = repository.save(updated);
        return usuarioMapper.toDto(res);
    }

    public void deleteMyself(String token) {
        Usuario me = repository.findByEmail(jwtService.extractEmail(token))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
        repository.delete(me);
    }
}
