package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.ComportamientoDto;
import com.edisonla.evaluacion_desempeno.dtos.ComportamientoRequest;
import com.edisonla.evaluacion_desempeno.entities.Comportamiento;
import com.edisonla.evaluacion_desempeno.entities.Evaluado;
import com.edisonla.evaluacion_desempeno.mappers.ComportamientoMapper;
import com.edisonla.evaluacion_desempeno.mappers.ComportamientoRequestMapper;
import com.edisonla.evaluacion_desempeno.mappers.EvaluadoMapper;
import com.edisonla.evaluacion_desempeno.mappers.EvaluadoRequestMapper;
import com.edisonla.evaluacion_desempeno.repositories.ComportamientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComportamientoService {

    private final ComportamientoRepository repository;

    public Iterable<ComportamientoDto> getAll() {
        return repository.findAll()
                .stream()
                .map(ComportamientoMapper::toDto)
                .toList();
    }

    public Comportamiento get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ComportamientoDto create(ComportamientoRequest dto) {
        Comportamiento res = repository.save(ComportamientoRequestMapper.toEntity(dto));
        return ComportamientoMapper.toDto(res);
    }

    public ComportamientoRequest update(Long id, ComportamientoRequest dto) {
        Comportamiento original = repository.findById(id).orElse(null);
        if(original == null) {
            return null;
        } else {
            Comportamiento updated = ComportamientoRequestMapper.toEntity(dto);
            updated.setId(original.getId());
            Comportamiento res = repository.save(updated);
            return ComportamientoRequestMapper.toDto(res);
        }
    }

    public boolean delete(Long id) {
        Comportamiento comportamiento = repository.findById(id).orElse(null);
        if (comportamiento == null) {
            return false;
        } else {
            repository.delete(comportamiento);
            return true;
        }
    }
}
