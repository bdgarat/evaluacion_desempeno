package com.edisonla.evaluacion_desempeno.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "competencia_cuantitativa")
public class CompetenciaCualitativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "devolucion")
    private String devolucion;

    @Column (name= "validado")
    private boolean validado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluado_id")
    @ToString.Exclude
    private Evaluado evaluador;


    public CompetenciaCualitativa(Long id, String nombre, String descripcion, String devolucion, boolean validado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.devolucion = devolucion;
        this.validado = validado;
    }

    public CompetenciaCualitativa(String nombre, String descripcion, String devolucion, boolean validado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.devolucion = devolucion;
        this.validado = validado;
    }
}
