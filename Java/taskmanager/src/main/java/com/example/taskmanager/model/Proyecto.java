package com.example.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_alumno;

    @Column
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private float presupuesto_estimado;

    @Column
    private float coste_interno;

    @Column
    private float coste_externo;

    @Column
    private float coste_total;

    @Column
    private String fase_actual;

    @Column
    private int creado_por;

    @Column
    private Date fecha_creacion;

}
