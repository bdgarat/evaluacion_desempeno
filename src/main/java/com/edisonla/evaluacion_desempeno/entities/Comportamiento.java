package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "calificacion")
    private double calificacion;

    @Column(name = "idPregunta", nullable = false)
    private String pregunta;

    @Column(name = "creado", nullable = false)
    private Date creado;

    @Column(name = "ultimaModificacion",  nullable = false)
    private Date ultimaModificacion;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "competencia_cuantitativa_id")
    @ToString.Exclude
    private CompetenciaCuantitativa competenciaCuantitativa;

}
