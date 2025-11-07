package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.EvaluadoDto;
import com.edisonla.evaluacion_desempeno.dtos.EvaluadoRequest;
import com.edisonla.evaluacion_desempeno.entities.Evaluado;
import com.edisonla.evaluacion_desempeno.mappers.EvaluadoMapper;
import com.edisonla.evaluacion_desempeno.mappers.EvaluadoRequestMapper;
import com.edisonla.evaluacion_desempeno.repositories.EvaluadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EvaluadoService {

    private final EvaluadoRepository repository;

    public Iterable<EvaluadoDto> getAll() {
        List<Evaluado> e = repository.findAll();
        return e
                .stream()
                .map(EvaluadoMapper::toDto) //method reference reemplazo de (e -> mapper.toDto(e))
                .toList();
    }

    public EvaluadoDto get(Long id) {
        Evaluado e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        return EvaluadoMapper.toDto(e);
    }

    @Transactional
    public EvaluadoDto create(EvaluadoRequest dto) {
        Evaluado e = repository.save(EvaluadoRequestMapper.toEntity(dto));
        return EvaluadoMapper.toDto(e);
    }

    @Transactional
    public EvaluadoRequest update(Long id, EvaluadoRequest dto) {
        Evaluado original = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        Evaluado updated = EvaluadoRequestMapper.toEntity(dto);
        updated.setId(original.getId());
        Evaluado res = repository.save(updated);
        return EvaluadoRequestMapper.toDto(res);
    }

    @Transactional
    public void delete(Long id) {
        Evaluado evaluado = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        repository.delete(evaluado);
    }

}
