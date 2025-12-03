package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.EvaluacionDto;
import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import com.edisonla.evaluacion_desempeno.mappers.EvaluacionMapper;
import com.edisonla.evaluacion_desempeno.repositories.EvaluacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EvaluacionService {

    private final EvaluacionRepository repository;
    private final EvaluacionMapper evaluacionMapper;

    @Transactional(readOnly = true)
    public Iterable<EvaluacionDto> getAll() {
        List<Evaluacion> e = repository.findAll();
        return e
                .stream()
                .map(evaluacionMapper::toDto) //method reference reemplazo de (e -> mapper.toDto(e))
                .toList();
    }

    @Transactional(readOnly = true)
    public EvaluacionDto get(Long id) {
        Evaluacion e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        return evaluacionMapper.toDto(e);
    }

    @Transactional
    public EvaluacionDto create(EvaluacionDto dto) {
        Evaluacion e = evaluacionMapper.toEntity(dto);
        e.setCreado(new Date());
        e.setUltimaModificacion(new Date());
        return evaluacionMapper.toDto(repository.save(e));
    }

    @Transactional
    public EvaluacionDto update(Long id, EvaluacionDto dto) {
        Evaluacion original = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        Evaluacion updated = evaluacionMapper.toEntity(dto);
        updated.setId(original.getId());
        updated.setUltimaModificacion(new Date());
        Evaluacion res = repository.save(updated);
        return evaluacionMapper.toDto(res);
    }

    @Transactional
    public void delete(Long id) {
        Evaluacion evaluacion = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        repository.delete(evaluacion);
    }
}



