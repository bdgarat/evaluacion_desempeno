package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "competencia")
public class Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "calificacion")
    private double calificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluacion_cuantitativa_id")
    @ToString.Exclude
    private EvaluacionCuantitativa evaluacionCuantitativa;


}
