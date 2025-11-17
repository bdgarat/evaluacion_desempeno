package com.edisonla.evaluacion_desempeno.controllers;

import com.edisonla.evaluacion_desempeno.dtos.ComportamientoDto;
import com.edisonla.evaluacion_desempeno.dtos.ComportamientoRequest;
import com.edisonla.evaluacion_desempeno.entities.Comportamiento;
import com.edisonla.evaluacion_desempeno.mappers.ComportamientoMapper;
import com.edisonla.evaluacion_desempeno.services.ComportamientoService;
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
@RequestMapping("/api/competencias-cuantitativas/{ccId}/comportamientos")
@CrossOrigin(origins = "*")
public class ComportamientoController {

    @Autowired
    private ComportamientoService service;

    private final ComportamientoMapper comportamientoMapper;

    private static final String urlBase = "/api/competencias-cuantitativas/{ccId}/comportamientos";

    @GetMapping
    public Iterable<ComportamientoDto> getAll(@PathVariable(name = "ccId") Long ccId) {
        return service.getAll(ccId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(name = "ccId") Long ccId,
                                                 @PathVariable long id) {
        try {
            Comportamiento comportamiento = service.get(ccId, id);
            return ResponseEntity.ok(comportamientoMapper.toDto(comportamiento));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@PathVariable(name = "ccId") Long ccId,
                                                    @RequestBody ComportamientoRequest request,
                                              UriComponentsBuilder uriBuilder) {
        try {
            ComportamientoDto dto = service.create(ccId, request);
            URI location = uriBuilder.path(urlBase + "/{id}").buildAndExpand(ccId, dto.id()).toUri();
            return ResponseEntity.created(location).body(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Object> update(@PathVariable(name = "ccId") Long ccId,
                                                        @PathVariable Long id,
                                                        @RequestBody ComportamientoRequest request) {
        try {
            ComportamientoRequest dto = service.update(ccId, id, request);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "ccId") Long ccId,
                                       @PathVariable Long id) {
        try {
            service.delete(ccId, id);
            return ResponseEntity.noContent().build();
        } catch (
        EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
}

}
