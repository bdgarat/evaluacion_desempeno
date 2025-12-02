package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "competencia_cualitativa")
public class CompetenciaCualitativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "devolucion")
    private String devolucion;

    @Column (name= "validado")
    private boolean validado;

    @Column(name = "idPregunta", nullable = false)
    private String idPregunta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluado_id")
    @ToString.Exclude
    private Evaluacion evaluacion;

}
