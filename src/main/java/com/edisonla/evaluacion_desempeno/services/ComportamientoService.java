package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.ComportamientoDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCuantitativa;
import com.edisonla.evaluacion_desempeno.entities.Comportamiento;
import com.edisonla.evaluacion_desempeno.mappers.*;
import com.edisonla.evaluacion_desempeno.repositories.CompetenciaCuantitativaRepository;
import com.edisonla.evaluacion_desempeno.repositories.ComportamientoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ComportamientoService {

    private final CompetenciaCuantitativaRepository competenciaCuantitativaRepository;
    private final ComportamientoRepository repository;
    private final ComportamientoMapper comportamientoMapper;

    @Transactional(readOnly = true)
    public Iterable<ComportamientoDto> getAll(Long ccId) {
        /*return repository.findAll()
                .stream()
                .filter(comp -> comp.getCompetenciaCuantitativa().getId().equals(ccId)) // Reemplazar por filtrar desde repo
                .map(comportamientoMapper::toDto)
                .toList();*/
        List<Comportamiento> l =  repository.findAllByCompetenciaCuantitativaId(ccId);
        return l.stream()
                .map(comportamientoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Comportamiento get(Long ccId, Long id) {
        Comportamiento comp = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comportamiento no encontrado: " + id));
        if (!comp.getCompetenciaCuantitativa().getId().equals(ccId)) {
            throw new IllegalArgumentException("Comportamiento " + id + " no pertenece a la competencia cuantitativa " + ccId);
        } else {
            return comp;
        }
    }

    @Transactional
    public ComportamientoDto create(Long ccId, ComportamientoDto dto) {
        CompetenciaCuantitativa cc = competenciaCuantitativaRepository.findById(ccId)
                .orElseThrow(() -> new EntityNotFoundException("Competencia Cuantitativa no encontrada: " + ccId));
        Comportamiento comp = comportamientoMapper.toEntity(dto);
        cc.addComportamiento(comp); // Establece la relación bidireccional
        comp.setCreado(new Date());
        comp.setUltimaModificacion(new Date());
        Comportamiento res = repository.save(comp);
        return comportamientoMapper.toDto(res);
    }

    @Transactional
    public ComportamientoDto update(Long ccId, Long id, ComportamientoDto dto) {
        Comportamiento original = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competencia Cuantitativa no encontrada: " + id));
        if (!original.getCompetenciaCuantitativa().getId().equals(ccId)) {
            throw new IllegalArgumentException("Comportamiento " + id + " no pertenece a la competencia cuantitativa " + ccId);
        } else {
            Comportamiento updated = comportamientoMapper.toEntity(dto);
            updated.setId(original.getId());
            updated.setUltimaModificacion(new Date());
            Comportamiento res = repository.save(updated);
            return comportamientoMapper.toDto(res);
        }
    }

    @Transactional
    public void delete(Long ccId, Long id) {
        Comportamiento comportamiento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comportamiento no encontrado: " + id));
        if (!comportamiento.getCompetenciaCuantitativa().getId().equals(ccId)) {
            throw new IllegalArgumentException("Comportamiento " + id + " no pertenece a la competencia cuantitativa " + ccId);
        } else {
            repository.delete(comportamiento);
        }
    }
}
