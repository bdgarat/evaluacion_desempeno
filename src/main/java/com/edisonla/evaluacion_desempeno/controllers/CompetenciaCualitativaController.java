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
@RequestMapping("/api/competencias-cualitativas")
@CrossOrigin(origins = "*")
public class CompetenciaCualitativaController {

    @Autowired
    CompetenciaCualitativaService service;

    private static final String urlBase = "/api/competencias-cualitativas";

    @GetMapping
    public Iterable<CompetenciaCualitativaDto> getAll(@RequestParam(name = "evaluacion") Long evaluacionId) {
        return service.getAll(evaluacionId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@RequestParam(name = "evaluacion") Long evaluacionId,
                                                         @PathVariable(name = "id") Long id) {
        try {
            CompetenciaCualitativa competenciaCualitativa =  service.get(evaluacionId, id);
            return ResponseEntity.ok(competenciaCualitativa);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestParam(name = "evaluacion") Long evaluacionId,
                                                            @RequestBody CompetenciaCualitativaDto request,
                                                             UriComponentsBuilder uriBuilder) {
        try {
            CompetenciaCualitativaDto dto = service.create(evaluacionId, request);
            URI location = uriBuilder.path(urlBase + "/{id}").buildAndExpand(evaluacionId, dto.id()).toUri();
            return ResponseEntity.created(location).body(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Object> update(@RequestParam(name = "evaluacion") Long evaluacionId,
                                                                @PathVariable Long id,
                                                             @RequestBody CompetenciaCualitativaDto request) {
        try {
            CompetenciaCualitativaDto dto = service.update(evaluacionId, id, request);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> delete(@RequestParam(name = "evaluacion") Long evaluacionId,
                                       @PathVariable Long id) {
        try {
            service.delete(evaluacionId, id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

}
