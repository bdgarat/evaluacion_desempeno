package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaDto;
import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCualitativaRequest;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCualitativa;
import com.edisonla.evaluacion_desempeno.entities.Evaluado;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCualitativaMapper;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCualitativaRequestMapper;
import com.edisonla.evaluacion_desempeno.repositories.CompetenciaCualitativaRepository;
import com.edisonla.evaluacion_desempeno.repositories.EvaluadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CompetenciaCualitativaService {

    private final CompetenciaCualitativaRepository repository;
    private final EvaluadoRepository evaluadoRepository;

    public Iterable<CompetenciaCualitativaDto> getAll(Long evaluadoId) {
        return repository.findAll()
                .stream()
                .filter(cc -> cc.getEvaluador().getId().equals(evaluadoId)) // Reemplazar por filtrar desde repo
                .map(CompetenciaCualitativaMapper::toDto) //method reference reemplazo de (evaluacionCualitativa -> evaluacionCualitativaMapper.toDto(evaluacionCualitativa))
                .toList();
    }

    public CompetenciaCualitativa get(Long evaluadoId, Long id) {
        CompetenciaCualitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        if(!cc.getEvaluador().getId().equals(evaluadoId)) {
            throw new IllegalArgumentException("Competencia Cualitativa " + id + " no pertenece al Evaluado " + evaluadoId);
        } else {
            return cc;
        }
    }

    @Transactional
    public CompetenciaCualitativaDto create(Long evaluadoId, CompetenciaCualitativaRequest dto) {
        Evaluado evaluado = evaluadoRepository.findById(evaluadoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + evaluadoId));

        CompetenciaCualitativa entidad = CompetenciaCualitativaRequestMapper.toEntity(dto);
        evaluado.addCompetenciaCualitativa(entidad); // Establece la relación bidireccional
        CompetenciaCualitativa res = repository.save(entidad);
        return CompetenciaCualitativaMapper.toDto(res);
    }

    @Transactional
    public CompetenciaCualitativaRequest update(Long evaluadoId, Long id, CompetenciaCualitativaRequest dto) {
        CompetenciaCualitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        if(!cc.getEvaluador().getId().equals(evaluadoId)) {
            throw new IllegalArgumentException("Competencia Cualitativa " + id + " no pertenece al Evaluado " + evaluadoId);
        } else {
            CompetenciaCualitativa updated = CompetenciaCualitativaRequestMapper.toEntity(dto);
            updated.setId(cc.getId());
            CompetenciaCualitativa res = repository.save(updated);
            return CompetenciaCualitativaRequestMapper.toDto(res);
        }
    }

    @Transactional
    public void delete(Long evaluadoId, Long id) {
        CompetenciaCualitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        if(!cc.getEvaluador().getId().equals(evaluadoId)) {
            throw new IllegalArgumentException("Competencia Cualitativa " + id + " no pertenece al Evaluado " + evaluadoId);
        } else {
            repository.delete(cc);
        }
    }
}
