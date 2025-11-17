package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;

    // Todos los evaluados donde este usuario es el evaluador
    @OneToMany(mappedBy = "evaluador", fetch = FetchType.LAZY)
    private List<Evaluado> evaluaciones = new ArrayList<>();

    // Todos los evaluados donde este usuario es el validador
    @OneToMany(mappedBy = "validador", fetch = FetchType.LAZY)
    private List<Evaluado> validaciones = new ArrayList<>();

}
