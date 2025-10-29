package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Evaluado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "incorporacion")
    private LocalDateTime incorporacion;

    @Column(name = "legajo")
    private String legajo;

    @Column(name = "resultado_final")
    private double resultadoFinal;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "es_admin")
    private boolean esAdmin;

    @OneToMany(mappedBy = "evaluador", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<EvaluacionCuantitativa> evaluacionesCuantitativas = new ArrayList<>();

    @OneToMany(mappedBy = "evaluador", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<EvaluacionCualitativa> evaluacionesCualitativas = new ArrayList<>();
    
}
