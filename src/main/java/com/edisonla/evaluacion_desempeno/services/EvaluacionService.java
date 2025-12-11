package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.EvaluacionDto;
import com.edisonla.evaluacion_desempeno.dtos.EvaluacionesResponse;
import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import com.edisonla.evaluacion_desempeno.entities.Usuario;
import com.edisonla.evaluacion_desempeno.enums.EstadoEvaluacion;
import com.edisonla.evaluacion_desempeno.mappers.EvaluacionMapper;
import com.edisonla.evaluacion_desempeno.repositories.EvaluacionRepository;
import com.edisonla.evaluacion_desempeno.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EvaluacionService {

    private final EvaluacionRepository repository;
    private final EvaluacionMapper evaluacionMapper;
    private final UsuarioRepository userRepository;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public Iterable<EvaluacionDto> getAll() {
        List<Evaluacion> e = repository.findAll();
        return e
                .stream()
                .map(evaluacionMapper::toDto) //method reference reemplazo de (e -> mapper.toDto(e))
                .toList();
    }

    @Transactional(readOnly = true)
    public EvaluacionDto get(Long id) {
        Evaluacion e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        return evaluacionMapper.toDto(e);
    }

    @Transactional
    public EvaluacionDto create(EvaluacionDto dto) {
        Optional<Usuario> evaluado = userRepository.findById(dto.evaluado());
        Optional<Usuario> evaluador = userRepository.findById(dto.evaluador());
        Optional<Usuario> validador = userRepository.findById(dto.validador());

        if(evaluado.isEmpty() || evaluador.isEmpty() || validador.isEmpty()) {
            throw new IllegalArgumentException("Verificar que evaluado/evaluador/validador exista");
        }
        Evaluacion e = evaluacionMapper.toEntity(dto);
        e.setId(null);
        e.setEstado(EstadoEvaluacion.PENDIENTE);
        e.setCreado(new Date());
        e.setUltimaModificacion(new Date());
        e.setEvaluado(evaluado.get());
        e.setEvaluador(evaluador.get());
        e.setValidador(validador.get());
        return evaluacionMapper.toDto(repository.save(e));
    }

    @Transactional
    public EvaluacionDto update(Long id, EvaluacionDto dto) {
        Evaluacion original = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        if(!(userRepository.existsById(dto.evaluado()) || userRepository.existsById(dto.evaluador()) ||
                userRepository.existsById(dto.validador()))) {
            throw new IllegalArgumentException("Verificar que el evaluado/evaluador/validador exista");
        }
        Evaluacion updated = evaluacionMapper.toEntity(dto);
        updated.setId(original.getId());
        updated.setUltimaModificacion(new Date());
        updated.setEvaluado(original.getEvaluado());
        updated.setEvaluador(original.getEvaluador());
        updated.setValidador(original.getValidador());
        Evaluacion res = repository.save(updated);
        return evaluacionMapper.toDto(res);
    }

    @Transactional
    public void delete(Long id) {
        Evaluacion evaluacion = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con id: " + id));
        repository.delete(evaluacion);
    }


    @Transactional(readOnly = true)
    public EvaluacionesResponse getEvaluacionesByToken(String token) {
        String email = jwtService.extractEmail(token);

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con email: " + email));

        Long userId = usuario.getId();

        List<Evaluacion> evaluaciones = repository.findAllByEvaluadorId(userId);   //MATI OBTIENE A BRUNO BRAIAN Y FACU
        List<Evaluacion> validaciones = repository.findAllByValidadorId(userId);  //CHARLY OBTIENE A MATI

        return new EvaluacionesResponse(evaluaciones, validaciones);
    }

}



