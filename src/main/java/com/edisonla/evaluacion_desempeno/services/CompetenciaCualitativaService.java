package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaRequest;
import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaRequest;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCuantitativa;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCualitativaMapper;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCualitativaRequestMapper;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCuantitativaRequestMapper;
import com.edisonla.evaluacion_desempeno.repositories.CompetenciaCualitativaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompetenciaCualitativaService {

    private final CompetenciaCualitativaRepository repository;

    public Iterable<CompetenciaCualitativaDto> getAll() {
        return repository.findAll()
                .stream()
                .map(CompetenciaCualitativaMapper::toDto) //method reference reemplazo de (evaluacionCualitativa -> evaluacionCualitativaMapper.toDto(evaluacionCualitativa))
                .toList();
    }

    public CompetenciaCualitativa get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public CompetenciaCualitativaDto create(CompetenciaCualitativaRequest dto) {
        return CompetenciaCualitativaMapper.toDto(repository.save(CompetenciaCualitativaRequestMapper.toEntity(dto)));
    }

    public CompetenciaCualitativaRequest update(Long id, CompetenciaCualitativaRequest dto) {
        CompetenciaCualitativa cc = repository.findById(id).orElse(null);
        if(cc == null) {
            return null;
        } else {
            CompetenciaCualitativa updated = CompetenciaCualitativaRequestMapper.toEntity(dto);
            updated.setId(cc.getId());
            CompetenciaCualitativa res = repository.save(updated);
            return CompetenciaCualitativaRequestMapper.toDto(res);
        }
    }

    public boolean delete(Long id) {
        CompetenciaCualitativa cc = repository.findById(id).orElse(null);
        if (cc == null) {
            return false;
        } else {
            repository.delete(cc);
            return true;
        }
    }
}
