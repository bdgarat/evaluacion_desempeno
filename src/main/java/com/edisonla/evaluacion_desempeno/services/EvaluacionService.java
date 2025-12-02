package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.EvaluacionDto;
import com.edisonla.evaluacion_desempeno.dtos.EvaluacionRequest;
import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import com.edisonla.evaluacion_desempeno.mappers.EvaluacionMapper;
import com.edisonla.evaluacion_desempeno.mappers.EvaluacionRequestMapper;
import com.edisonla.evaluacion_desempeno.repositories.EvaluacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EvaluacionService {

    private final EvaluacionRepository repository;
    private final EvaluacionMapper evaluacionMapper;
    private final EvaluacionRequestMapper evaluacionRequestMapper;

    public Iterable<EvaluacionDto> getAll() {
        List<Evaluacion> e = repository.findAll();
        return e
                .stream()
                .map(evaluacionMapper::toDto) //method reference reemplazo de (e -> mapper.toDto(e))
                .toList();
    }

    public EvaluacionDto get(Long id) {
        Evaluacion e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        return evaluacionMapper.toDto(e);
    }

    @Transactional
    public EvaluacionDto create(EvaluacionRequest dto) {
        Evaluacion e = repository.save(evaluacionRequestMapper.toEntity(dto));
        return evaluacionMapper.toDto(e);
    }

    @Transactional
    public EvaluacionRequest update(Long id, EvaluacionRequest dto) {
        Evaluacion original = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        Evaluacion updated = evaluacionRequestMapper.toEntity(dto);
        updated.setId(original.getId());
        Evaluacion res = repository.save(updated);
        return evaluacionRequestMapper.toDto(res);
    }

    @Transactional
    public void delete(Long id) {
        Evaluacion evaluacion = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        repository.delete(evaluacion);
    }
}



