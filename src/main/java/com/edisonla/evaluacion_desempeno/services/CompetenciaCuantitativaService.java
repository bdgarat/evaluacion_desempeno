package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaDto;
import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaRequest;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCuantitativa;
import com.edisonla.evaluacion_desempeno.entities.Comportamiento;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCuantitativaMapper;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCuantitativaRequestMapper;
import com.edisonla.evaluacion_desempeno.mappers.ComportamientoRequestMapper;
import com.edisonla.evaluacion_desempeno.repositories.CompetenciaCuantitativaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompetenciaCuantitativaService {

    private final CompetenciaCuantitativaRepository repository;

    public Iterable<CompetenciaCuantitativaDto> getAll() {
        return repository.findAll()
                .stream()
                .map(CompetenciaCuantitativaMapper::toDto) //method reference reemplazo de (evaluacionCualitativa -> evaluacionCualitativaMapper.toDto(evaluacionCualitativa))
                .toList();
    }

    public CompetenciaCuantitativa get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public CompetenciaCuantitativaDto create(CompetenciaCuantitativaRequest dto) {
        return CompetenciaCuantitativaMapper.toDto(repository.save(CompetenciaCuantitativaRequestMapper.toEntity(dto)));
    }

    public CompetenciaCuantitativaRequest update(Long id, CompetenciaCuantitativaRequest dto) {
        CompetenciaCuantitativa cc = repository.findById(id).orElse(null);
        if(cc == null) {
            return null;
        } else {
            CompetenciaCuantitativa updated = CompetenciaCuantitativaRequestMapper.toEntity(dto);
            updated.setId(cc.getId());
            CompetenciaCuantitativa res = repository.save(updated);
            return CompetenciaCuantitativaRequestMapper.toDto(res);
        }
    }

    public boolean delete(Long id) {
        CompetenciaCuantitativa cc = repository.findById(id).orElse(null);
        if (cc == null) {
            return false;
        } else {
            repository.delete(cc);
            return true;
        }
    }

}
