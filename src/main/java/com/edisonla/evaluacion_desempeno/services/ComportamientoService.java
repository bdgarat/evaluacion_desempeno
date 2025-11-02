package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.ComportamientoDto;
import com.edisonla.evaluacion_desempeno.entities.Comportamiento;
import com.edisonla.evaluacion_desempeno.mappers.ComportamientoMapper;
import com.edisonla.evaluacion_desempeno.repositories.ComportamientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComportamientoService {

    private final ComportamientoRepository comportamientoRepository;

    private final ComportamientoMapper comportamientoMapper;

    public Iterable<ComportamientoDto> getAll() {
        return comportamientoRepository.findAll()
                .stream()
                .map(comportamientoMapper::toDto)
                .toList();
    }

    public Comportamiento get(Long id) {
        return comportamientoRepository.findById(id).orElse(null);
    }
}
