package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCualitativaMapper;
import com.edisonla.evaluacion_desempeno.repositories.CompetenciaCualitativaRepository;
import com.edisonla.evaluacion_desempeno.repositories.EvaluacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CompetenciaCualitativaService {

    private final CompetenciaCualitativaRepository repository;
    private final EvaluacionRepository evaluacionRepository;

    @Autowired
    private final CompetenciaCualitativaMapper ccMapper;

    @Transactional(readOnly = true)
    public Iterable<CompetenciaCualitativaDto> getAll(Long evaluacionId) {
        /*return repository.findAll()
                .stream()
                .filter(cc -> cc.getEvaluacion().getId().equals(evaluacionId)) // Reemplazar por filtrar desde repo
                .map(ccMapper::toDto) //method reference reemplazo de (evaluacionCualitativa -> evaluacionCualitativaMapper.toDto(evaluacionCualitativa))
                .toList();*/
        List<CompetenciaCualitativa> l =  repository.findAllByEvaluacionId(evaluacionId);
        return l.stream()
                .map(ccMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
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
    public CompetenciaCualitativaDto create(Long evaluacionId, CompetenciaCualitativaDto dto) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + evaluacionId));

        CompetenciaCualitativa entidad = ccMapper.toEntity(dto);
        entidad.setCreado(new Date());
        entidad.setUltimaModificacion(new Date());
        //evaluacion.addCompetenciaCualitativa(entidad); // Establece la relación bidireccional
        entidad.setEvaluacion(evaluacion);
        CompetenciaCualitativa res = repository.save(entidad);
        return ccMapper.toDto(res);
    }

    @Transactional
    public CompetenciaCualitativaDto update(Long evaluacionId, Long id, CompetenciaCualitativaDto dto) {
        CompetenciaCualitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        if(!cc.getEvaluacion().getId().equals(evaluacionId)) {
            throw new IllegalArgumentException("Competencia Cualitativa " + id + " no pertenece a la evaluacion " + evaluacionId);
        } else {
            CompetenciaCualitativa updated = ccMapper.toEntity(dto);
            updated.setId(cc.getId());
            updated.setUltimaModificacion(new Date());
            updated.setEvaluacion(cc.getEvaluacion());
            CompetenciaCualitativa res = repository.save(updated);
            return ccMapper.toDto(res);
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
