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
@Table(name = "competencia_cuantitativa")
public class CompetenciaCuantitativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "resultado")
    private double resultado;

    @Column (name= "validado")
    private boolean validado;

    @Column(name = "idPregunta", nullable = false)
    private String idPregunta;

    @OneToMany(mappedBy = "competenciaCuantitativa", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Comportamiento> comportamientos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluado_id")
    @ToString.Exclude
    private Evaluacion evaluacion;

    public void addComportamiento(Comportamiento comportamiento) {
        this.comportamientos.add(comportamiento);
        comportamiento.setCompetenciaCuantitativa(this);
    }
}
