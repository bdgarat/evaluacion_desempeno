package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "evaluaciones")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 64, nullable = false)
    private String nombre;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "resultadoCalculado")
    private double resultadoCalculado;

    @Column(name ="resultadoEscrito", length = 32)
    private String resultadoEscrito;

    @Column(name ="seniority", length = 32)
    private String seniority;

    @Column(name ="disponibilidad", length = 16)
    private String disponibilidad;

    @Column(name ="puesto", length = 24)
    private String puesto;

    //clave foranea hacia user (rol evaluado)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario evaluado;

    //clave foranea hacia user (rol evaluador)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario evaluador;

    //clave foranea hacia user (rol validador)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario validador;


    @OneToMany(mappedBy = "evaluacion", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CompetenciaCuantitativa> competenciasCuantitativas = new ArrayList<>();

    @OneToMany(mappedBy = "evaluacion", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CompetenciaCualitativa> competenciasCualitativas = new ArrayList<>();


    public void addCompetenciaCuantitativa(CompetenciaCuantitativa competencia) {
        this.competenciasCuantitativas.add(competencia);
        competencia.setEvaluacion(this);
    }

    public void addCompetenciaCualitativa(CompetenciaCualitativa competencia) {
        this.competenciasCualitativas.add(competencia);
        competencia.setEvaluacion(this);
    }
}
