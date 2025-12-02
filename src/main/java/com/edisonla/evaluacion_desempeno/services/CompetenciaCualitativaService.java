package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaRequest;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCualitativaMapper;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCualitativaRequestMapper;
import com.edisonla.evaluacion_desempeno.repositories.CompetenciaCualitativaRepository;
import com.edisonla.evaluacion_desempeno.repositories.EvaluacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CompetenciaCualitativaService {

    private final CompetenciaCualitativaRepository repository;
    private final EvaluacionRepository evaluacionRepository;

    @Autowired
    private final CompetenciaCualitativaMapper ccMapper;
    @Autowired
    private final CompetenciaCualitativaRequestMapper ccRequestMapper;

    public Iterable<CompetenciaCualitativaDto> getAll(Long evaluacionId) {
        return repository.findAll()
                .stream()
                .filter(cc -> cc.getEvaluacion().getId().equals(evaluacionId)) // Reemplazar por filtrar desde repo
                .map(ccMapper::toDto) //method reference reemplazo de (evaluacionCualitativa -> evaluacionCualitativaMapper.toDto(evaluacionCualitativa))
                .toList();
    }

    public CompetenciaCualitativa get(Long evaluacionId, Long id) {
        CompetenciaCualitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        if(!cc.getEvaluacion().getId().equals(evaluacionId)) {
            throw new IllegalArgumentException("Competencia Cualitativa " + id + " no pertenece a la evaluacion " + evaluacionId);
        } else {
            return cc;
        }
    }

    @Transactional
    public CompetenciaCualitativaDto create(Long evaluacionId, CompetenciaCualitativaRequest dto) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + evaluacionId));

        CompetenciaCualitativa entidad = ccRequestMapper.toEntity(dto);
        evaluacion.addCompetenciaCualitativa(entidad); // Establece la relación bidireccional
        CompetenciaCualitativa res = repository.save(entidad);
        return ccMapper.toDto(res);
    }

    @Transactional
    public CompetenciaCualitativaRequest update(Long evaluacionId, Long id, CompetenciaCualitativaRequest dto) {
        CompetenciaCualitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        if(!cc.getEvaluacion().getId().equals(evaluacionId)) {
            throw new IllegalArgumentException("Competencia Cualitativa " + id + " no pertenece a la evaluacion " + evaluacionId);
        } else {
            CompetenciaCualitativa updated = ccRequestMapper.toEntity(dto);
            updated.setId(cc.getId());
            CompetenciaCualitativa res = repository.save(updated);
            return ccRequestMapper.toDto(res);
        }
    }

    @Transactional
    public void delete(Long evaluacionId, Long id) {
        CompetenciaCualitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        if(!cc.getEvaluacion().getId().equals(evaluacionId)) {
            throw new IllegalArgumentException("Competencia Cualitativa " + id + " no pertenece a la evaluacion " + evaluacionId);
        } else {
            repository.delete(cc);
        }
    }
}
