package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.EvaluadoDto;
import com.edisonla.evaluacion_desempeno.dtos.EvaluadoRequest;
import com.edisonla.evaluacion_desempeno.entities.Evaluado;
import com.edisonla.evaluacion_desempeno.mappers.EvaluadoMapper;
import com.edisonla.evaluacion_desempeno.mappers.EvaluadoRequestMapper;
import com.edisonla.evaluacion_desempeno.repositories.EvaluadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        Evaluado e = repository.findById(id).orElse(null);
        if (e == null) { return null; }
        return EvaluadoMapper.toDto(e);
    }

    public EvaluadoDto create(EvaluadoRequest dto) {
        Evaluado e = repository.save(EvaluadoRequestMapper.toEntity(dto));
        return EvaluadoMapper.toDto(e);
    }

    public EvaluadoRequest update(Long id, EvaluadoRequest dto) {
        Evaluado original = repository.findById(id).orElse(null);
        if(original == null) {
            return null;
        } else {
            Evaluado updated = EvaluadoRequestMapper.toEntity(dto);
            updated.setId(original.getId());
            Evaluado res = repository.save(updated);
            return EvaluadoRequestMapper.toDto(res);
        }
    }

    public boolean delete(Long id) {
        Evaluado evaluado = repository.findById(id).orElse(null);
        if (evaluado == null) {
            return false;
        } else {
            repository.delete(evaluado);
            return true;
        }
    }
}
