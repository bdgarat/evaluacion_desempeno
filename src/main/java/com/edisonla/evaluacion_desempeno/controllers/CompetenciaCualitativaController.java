package com.edisonla.evaluacion_desempeno.controllers;


import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import com.edisonla.evaluacion_desempeno.services.CompetenciaCualitativaService;
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
@RequestMapping("/api/evaluaciones/{evaluacionId}/competencias-cualitativas")
@CrossOrigin(origins = "*")
public class CompetenciaCualitativaController {

    @Autowired
    CompetenciaCualitativaService service;

    private static final String urlBase = "/api/evaluaciones/{evaluacionId}/competencias-cualitativas";

    @GetMapping
    public Iterable<CompetenciaCualitativaDto> getAll(@PathVariable(name = "evaluacionId") Long evaluadoId) {
        return service.getAll(evaluadoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(name = "evaluacionId") Long evaluadoId,
                                                         @PathVariable(name = "id") Long id) {
        try {
            CompetenciaCualitativa competenciaCualitativa =  service.get(evaluadoId, id);
            return ResponseEntity.ok(competenciaCualitativa);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@PathVariable(name = "evaluacionId") Long evaluadoId,
                                                            @RequestBody CompetenciaCualitativaDto request,
                                                             UriComponentsBuilder uriBuilder) {
        try {
            CompetenciaCualitativaDto dto = service.create(evaluadoId, request);
            URI location = uriBuilder.path(urlBase + "/{id}").buildAndExpand(evaluadoId, dto.id()).toUri();
            return ResponseEntity.created(location).body(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Object> update(@PathVariable(name = "evaluacionId") Long evaluadoId,
                                                                @PathVariable Long id,
                                                             @RequestBody CompetenciaCualitativaDto request) {
        try {
            CompetenciaCualitativaDto dto = service.update(evaluadoId, id, request);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "evaluacionId") Long evaluadoId,
                                       @PathVariable Long id) {
        try {
            service.delete(evaluadoId, id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

}
