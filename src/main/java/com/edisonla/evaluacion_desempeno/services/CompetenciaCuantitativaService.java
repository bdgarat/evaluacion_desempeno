package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCuantitativa;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCuantitativaMapper;
import com.edisonla.evaluacion_desempeno.repositories.CompetenciaCuantitativaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompetenciaCuantitativaService {

    private final CompetenciaCuantitativaRepository competenciaCuantitativaRepository;

    private final CompetenciaCuantitativaMapper competenciaCuantitativaMapper;

    public Iterable<CompetenciaCuantitativaDto> getAll() {
        return competenciaCuantitativaRepository.findAll()
                .stream()
                .map(competenciaCuantitativaMapper::toDto) //method reference reemplazo de (evaluacionCualitativa -> evaluacionCualitativaMapper.toDto(evaluacionCualitativa))
                .toList();
    }

    public CompetenciaCuantitativa get(Long id) {
        return competenciaCuantitativaRepository.findById(id).orElse(null);
    }


}
