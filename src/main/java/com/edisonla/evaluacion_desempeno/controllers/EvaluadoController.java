package com.edisonla.evaluacion_desempeno.controllers;

import com.edisonla.evaluacion_desempeno.dtos.EvaluadoDto;
import com.edisonla.evaluacion_desempeno.dtos.EvaluadoRequest;
import com.edisonla.evaluacion_desempeno.services.EvaluadoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/evaluados")
public class EvaluadoController {

    @Autowired
    EvaluadoService service;

    private static final String urlBase = "/api/evaluados";

    @GetMapping
    public Iterable<EvaluadoDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(name = "id") Long id) {
        try {
            EvaluadoDto dto =  service.get(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody EvaluadoRequest request,
                                              UriComponentsBuilder uriBuilder) {

        EvaluadoDto dto = service.create(request);
        URI location = uriBuilder.path(urlBase + "/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody EvaluadoRequest request) {
        try {
            EvaluadoRequest dto = service.update(id, request);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
