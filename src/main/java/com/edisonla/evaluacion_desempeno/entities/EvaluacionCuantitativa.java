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
@Table(name = "evaluacion_cuantitativa")
public class EvaluacionCuantitativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "resultado")
    private double resultado;

    @OneToMany(mappedBy = "evaluacionCuantitativa", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Competencia> competencias = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluado_id")
    @ToString.Exclude
    private Evaluado evaluado;



    private  double  getCalculoCompeticiones(List<Competencia> competencias) {
        return (competencias.stream().mapToDouble(Competencia::getCalificacion).sum() / competencias.size());  // retornamos el promedio subtotal de todas las calificaciones para esas cuntitativas
    }

    public double getResultadoCompeticiones(){
        return this.getCalculoCompeticiones(this.competencias);
    }


}
