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

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "incorporacion")
    private Date incorporacion;

    @Column(name = "legajo")
    private int legajo;

    @Column(name = "resultado_final")
    private double resultadoFinal;

    @Column(name = "username")
    private String username;

    @Column(name = "mail")
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "es_admin")
    private boolean admin;

    @OneToMany(mappedBy = "evaluador", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CompetenciaCuantitativa> competenciasCuantitativas = new ArrayList<>();

    @OneToMany(mappedBy = "evaluador", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CompetenciaCualitativa> competenciasCualitativas = new ArrayList<>();

    public Evaluado(String nombre, String apellido, Date incorporacion, int legajo, double resultadoFinal, String username, String mail, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.incorporacion = incorporacion;
        this.legajo = legajo;
        this.resultadoFinal = resultadoFinal;
        this.username = username;
        this.admin = false;
        this.mail = mail;
    }

    public Evaluado(Long id, String nombre, String apellido, Date incorporacion, int legajo, double v, String username, String mail, String password, boolean b) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.incorporacion = incorporacion;
        this.legajo = legajo;
        this.resultadoFinal = v;
        this.username = username;
        this.password = password;
        this.admin = b;
        this.mail = mail;
    }

    public Evaluado(String nombre, String apellido, Date incorporacion, int legajo, double v, String username, String mail) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.incorporacion = incorporacion;
        this.legajo = legajo;
        this.resultadoFinal = v;
        this.username = username;
        this.mail = mail;
    }

    public void addCompetenciaCuantitativa(CompetenciaCuantitativa competencia) {
        this.competenciasCuantitativas.add(competencia);
        competencia.setEvaluador(this);
    }

    public void addCompetenciaCualitativa(CompetenciaCualitativa competencia) {
        this.competenciasCualitativas.add(competencia);
        competencia.setEvaluador(this);
    }
}
