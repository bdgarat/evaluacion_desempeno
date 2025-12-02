package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comportamiento")
public class Comportamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "calificacion")
    private double calificacion;

    @Column(name = "idPregunta", nullable = false)
    private String idPregunta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "competencia_cuantitativa_id")
    @ToString.Exclude
    private CompetenciaCuantitativa competenciaCuantitativa;

}
