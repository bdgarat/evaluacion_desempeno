package com.edisonla.evaluacion_desempeno.controllers;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaDto;
import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaRequest;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCuantitativa;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCuantitativaMapper;
import com.edisonla.evaluacion_desempeno.services.CompetenciaCuantitativaService;
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
@RequestMapping("/api/evaluados/{evaluadoId}/competencias-cuantitativas")
public class CompetenciaCuantitativaController {
    @Autowired
    CompetenciaCuantitativaService service;

    private static final String urlBase = "/api/evaluados/{evaluadoId}/competencias-cuantitativas";

    @GetMapping
    public Iterable<CompetenciaCuantitativaDto> getAll(@PathVariable(name = "evaluadoId") Long evaluadoId) {
        return service.getAll(evaluadoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(name = "evaluadoId") Long evaluadoId,
                                      @PathVariable(name = "id") Long id) {
        try {
            CompetenciaCuantitativa competenciaCuantitativa =  service.get(evaluadoId, id);
            return ResponseEntity.ok(competenciaCuantitativa);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@PathVariable(name = "evaluadoId") Long evaluadoId,
                                         @RequestBody CompetenciaCuantitativaRequest request,
                                         UriComponentsBuilder uriBuilder) {
        try {
            CompetenciaCuantitativaDto dto = service.create(evaluadoId, request);
            URI location = uriBuilder.path(urlBase + "/{id}").buildAndExpand(dto.id()).toUri();
            return ResponseEntity.created(location).body(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Object> update(@PathVariable(name = "evaluadoId") Long evaluadoId,
                                         @PathVariable Long id,
                                         @RequestBody CompetenciaCuantitativaRequest request) {
        try {
            CompetenciaCuantitativaRequest dto = service.update(evaluadoId, id, request);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "evaluadoId") Long evaluadoId,
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
