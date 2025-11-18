package com.edisonla.evaluacion_desempeno.controllers;


import com.edisonla.evaluacion_desempeno.dtos.UsuarioDto;
import com.edisonla.evaluacion_desempeno.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    private static final String urlBase = "/api/usuarios";

    @GetMapping
    public ResponseEntity<Object> whoAmI(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        try {
            UsuarioDto dto = service.whoAmI(token);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        try {
            Iterable<UsuarioDto> list = service.getAll(token);
            return ResponseEntity.ok(list);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                             @RequestBody UsuarioDto usuarioDto) {
        try {
            UsuarioDto dto = service.updateMyself(token, usuarioDto);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        try {
            service.deleteMyself(token);
            return null;
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
