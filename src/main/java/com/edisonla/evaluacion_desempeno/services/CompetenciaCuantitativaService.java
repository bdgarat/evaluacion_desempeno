package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaDto;
import com.edisonla.evaluacion_desempeno.dtos.CompetenciaCuantitativaRequest;
import com.edisonla.evaluacion_desempeno.entities.CompetenciaCuantitativa;
import com.edisonla.evaluacion_desempeno.entities.Comportamiento;
import com.edisonla.evaluacion_desempeno.entities.Evaluado;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCuantitativaMapper;
import com.edisonla.evaluacion_desempeno.mappers.CompetenciaCuantitativaRequestMapper;
import com.edisonla.evaluacion_desempeno.mappers.ComportamientoRequestMapper;
import com.edisonla.evaluacion_desempeno.repositories.CompetenciaCuantitativaRepository;
import com.edisonla.evaluacion_desempeno.repositories.EvaluadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CompetenciaCuantitativaService {

    private final CompetenciaCuantitativaRepository repository;
    private final EvaluadoRepository evaluadoRepository;

    public Iterable<CompetenciaCuantitativaDto> getAll(Long evaluadoId) {
        return repository.findAll()
                .stream()
                .filter(cc -> cc.getEvaluador().getId().equals(evaluadoId)) // Reemplazar por filtrar desde repo
                .map(CompetenciaCuantitativaMapper::toDto) //method reference reemplazo de (evaluacionCuantitativa -> evaluacionCuantitativaMapper.toDto(evaluacionCuantitativa))
                .toList();
    }

    public CompetenciaCuantitativa get(Long evaluadoId, Long id) {
        CompetenciaCuantitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competencia Cuantitativa no encontrada: " + id));
        if(!cc.getEvaluador().getId().equals(evaluadoId)) {
            throw new IllegalArgumentException("Competencia Cuantitativa " + id + " no pertenece al Evaluado " + evaluadoId);
        } else {
            return cc;
        }
    }

    @Transactional
    public CompetenciaCuantitativaDto create(Long evaluadoId, CompetenciaCuantitativaRequest dto) {
        Evaluado evaluado = evaluadoRepository.findById(evaluadoId)
                .orElseThrow(() -> new EntityNotFoundException("Evaluado no encontrado: " + evaluadoId));

        CompetenciaCuantitativa entidad = CompetenciaCuantitativaRequestMapper.toEntity(dto);
        evaluado.addCompetenciaCuantitativa(entidad); // Establece la relación bidireccional
        CompetenciaCuantitativa res = repository.save(entidad);
        return CompetenciaCuantitativaMapper.toDto(res);
    }

    @Transactional
    public CompetenciaCuantitativaRequest update(Long evaluadoId, Long id, CompetenciaCuantitativaRequest dto) {
        CompetenciaCuantitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competencia Cuantitativa no encontrada: " + id));
        if(!cc.getEvaluador().getId().equals(evaluadoId)) {
            throw new IllegalArgumentException("Competencia Cuantitativa " + id + " no pertenece al Evaluado " + evaluadoId);
        } else {
            CompetenciaCuantitativa updated = CompetenciaCuantitativaRequestMapper.toEntity(dto);
            updated.setId(cc.getId());
            CompetenciaCuantitativa res = repository.save(updated);
            return CompetenciaCuantitativaRequestMapper.toDto(res);
        }
    }

    @Transactional
    public void delete(Long evaluadoId, Long id) {
        CompetenciaCuantitativa cc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competencia Cuantitativa no encontrada: " + id));
        if(!cc.getEvaluador().getId().equals(evaluadoId)) {
            throw new IllegalArgumentException("Competencia Cuantitativa " + id + " no pertenece al Evaluado " + evaluadoId);
        } else {
            repository.delete(cc);
        }
    }

}
