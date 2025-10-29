package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "evaluacion_cuantitativa")
public class EvaluacionCualitativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "devolucion")
    private String devolucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluado_id")
    @ToString.Exclude
    private Evaluado evaluado;


}
