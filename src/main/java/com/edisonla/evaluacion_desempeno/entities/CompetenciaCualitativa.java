package com.edisonla.evaluacion_desempeno.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "devolucion")
    private String devolucion;

    @Column (name= "validado")
    private boolean validado;

    @Column(name = "pregunta", nullable = false)
    private String pregunta;

    @Column(name = "creado", nullable = false)
    private Date creado;

    @Column(name = "ultimaModificacion",  nullable = false)
    private Date ultimaModificacion;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluado_id")
    @ToString.Exclude
    @JsonBackReference
    private Evaluacion evaluacion;

}
