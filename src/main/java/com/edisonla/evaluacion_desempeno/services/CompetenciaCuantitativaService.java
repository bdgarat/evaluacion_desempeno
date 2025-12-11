package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCuantitativa;
import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCuantitativaMapper;
import com.edisonla.evaluacion_desempeno.repositories.CompetenciaCuantitativaRepository;
import com.edisonla.evaluacion_desempeno.repositories.EvaluacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CompetenciaCuantitativaService {

    private final CompetenciaCuantitativaRepository repository;
    private final EvaluacionRepository evaluacionRepository;
    private final CompetenciaCuantitativaMapper ccMapper;

    public Iterable<CompetenciaCuantitativaDto> getAll(Long evaluacionId) {
        /*return repository.findAll()
                .stream()
                .filter(cc -> cc.getEvaluacion().getId().equals(evaluacionId)) // Reemplazar por filtrar desde repo
                .map(competenciaCuantitativaMapper::toDto) //method reference reemplazo de (evaluacionCuantitativa -> evaluacionCuantitativaMapper.toDto(evaluacionCuantitativa))
                .toList();*/
        List<CompetenciaCuantitativa> l =  repository.findAllByEvaluacionId(evaluacionId);
        return l.stream()
                .map(ccMapper::toDto)
                .toList();

    }

    @Transactional(readOnly = true)
    public CompetenciaCuantitativa get(Long evaluacionId, Long id) {
        CompetenciaCuantitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competencia Cuantitativa no encontrada: " + id));
        if(!cc.getEvaluacion().getId().equals(evaluacionId)) {
            throw new IllegalArgumentException("Competencia Cuantitativa " + id + " no pertenece al Evaluacion " + evaluacionId);
        } else {
            return cc;
        }
    }

    @Transactional
    public CompetenciaCuantitativaDto create(Long evaluacionId, CompetenciaCuantitativaDto dto) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
                .orElseThrow(() -> new EntityNotFoundException("Evaluacion no encontrado: " + evaluacionId));

        CompetenciaCuantitativa entidad = ccMapper.toEntity(dto);
        entidad.setCreado(new Date());
        entidad.setUltimaModificacion(new Date());
        //evaluacion.addCompetenciaCuantitativa(entidad); // Establece la relación bidireccional
        entidad.setEvaluacion(evaluacion);
        CompetenciaCuantitativa res = repository.save(entidad);
        return ccMapper.toDto(res);
    }

    @Transactional
    public CompetenciaCuantitativaDto update(Long evaluacionId, Long id, CompetenciaCuantitativaDto dto) {
        CompetenciaCuantitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competencia Cuantitativa no encontrada: " + id));
        if(!cc.getEvaluacion().getId().equals(evaluacionId)) {
            throw new IllegalArgumentException("Competencia Cuantitativa " + id + " no pertenece a la evaluacion " + evaluacionId);
        } else {
            CompetenciaCuantitativa updated = ccMapper.toEntity(dto);
            updated.setId(cc.getId());
            updated.setUltimaModificacion(new Date());
            updated.setEvaluacion(cc.getEvaluacion());
            CompetenciaCuantitativa res = repository.save(updated);
            return ccMapper.toDto(res);
        }
    }

    @Transactional
    public void delete(Long evaluacionId, Long id) {
        CompetenciaCuantitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competencia Cuantitativa no encontrada: " + id));
        if(!cc.getEvaluacion().getId().equals(evaluacionId)) {
            throw new IllegalArgumentException("Competencia Cuantitativa " + id + " no pertenece al Evaluado " + evaluacionId);
        } else {
            repository.delete(cc);
        }
    }

}
