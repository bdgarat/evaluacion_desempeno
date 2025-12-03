package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;

    @Column(name = "incorporacion")
    private LocalDate incorporacion;

    @Column(name = "legajo")
    private int legajo;

    @Column(name = "cuil")
    private String cuil;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "creado", nullable = false)
    private Date creado;

    @Column(name = "ultimaModificacion",  nullable = false)
    private Date ultimaModificacion;

    // Todas las evaluaciones donde este usuario es el evaluador
    @OneToMany(mappedBy = "evaluador", fetch = FetchType.LAZY)
    private List<Evaluacion> evaluacionesAjenas = new ArrayList<>();

    // Todas las evaluaciones donde este usuario es el validador
    @OneToMany(mappedBy = "validador", fetch = FetchType.LAZY)
    private List<Evaluacion> validaciones = new ArrayList<>();

    // Todas las evaluaciones donde este usuario es el evaluado
    @OneToMany(mappedBy = "validador", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Evaluacion> evaluacionesPropias = new ArrayList<>();
}
