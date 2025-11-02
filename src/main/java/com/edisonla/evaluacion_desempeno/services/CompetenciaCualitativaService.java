package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCualitativaMapper;
import com.edisonla.evaluacion_desempeno.repositories.CompetenciaCualitativaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompetenciaCualitativaService {

    private final CompetenciaCualitativaRepository competenciaCualitativaRepository;

    private final CompetenciaCualitativaMapper competenciaCualitativaMapper;

    public Iterable<CompetenciaCualitativaDto> getAll() {
        return competenciaCualitativaRepository.findAll()
                .stream()
                .map(competenciaCualitativaMapper::toDto) //method reference reemplazo de (evaluacionCualitativa -> evaluacionCualitativaMapper.toDto(evaluacionCualitativa))
                .toList();
    }

    public CompetenciaCualitativa get(Long id) {
        return competenciaCualitativaRepository.findById(id).orElse(null);
    }

}
