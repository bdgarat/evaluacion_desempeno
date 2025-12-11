package com.edisonla.evaluacion_desempeno.dtos;

import com.edisonla.evaluacion_desempeno.entities.Evaluacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public record EvaluacionesResponse ( List<Evaluacion> evaluaciones,List<Evaluacion> validaciones){
}