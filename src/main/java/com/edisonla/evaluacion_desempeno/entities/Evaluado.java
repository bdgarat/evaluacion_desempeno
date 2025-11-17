package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "evaluados")
public class Evaluado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="dni")
    private String dni;

    @Column(name="cuil")
    private String cuil;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "legajo")
    private int legajo;

    @Column(name = "incorporacion")
    private Date incorporacion;

    @Column(name = "seniority")
    private String seniority;

    @Column(name = "disponibilidad")
    private String disponibilidad;

    @Column(name = "mail")
    private String mail;

    @Column(name = "celula")
    private String celula;

    @Column(name = "equipo_metodologico")
    private String equipoMetodologico;

    @Column(name = "nuevo_puesto")
    private String nuevoPuesto;

    @Column(name ="puesto")
    private String puesto;

    //clave foranea hacia user (rol evaluador)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVALUADOR_ID")
    private Usuario evaluador;

    //clave foranea hacia user (rol validador)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VALIDADOR_ID")
    private Usuario validador;

    @Column(name = "resultado_final")
    private double resultadoFinal;


    @OneToMany(mappedBy = "evaluador", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CompetenciaCuantitativa> competenciasCuantitativas = new ArrayList<>();

    @OneToMany(mappedBy = "evaluador", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CompetenciaCualitativa> competenciasCualitativas = new ArrayList<>();


    public void addCompetenciaCuantitativa(CompetenciaCuantitativa competencia) {
        this.competenciasCuantitativas.add(competencia);
        competencia.setEvaluador(this);
    }

    public void addCompetenciaCualitativa(CompetenciaCualitativa competencia) {
        this.competenciasCualitativas.add(competencia);
        competencia.setEvaluador(this);
    }
}
