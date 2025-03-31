package com.example.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="proyectos")
public class Proyecto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    public Proyecto(String nombre, String descripcion, float presupuesto_estimado, float coste_interno, float coste_externo, float coste_total, String fase_actual, int creado_por, Date fecha_creacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presupuesto_estimado = presupuesto_estimado;
        this.coste_interno = coste_interno;
        this.coste_externo = coste_externo;
        this.coste_total = coste_total;
        this.fase_actual = fase_actual;
        this.creado_por = creado_por;
        this.fecha_creacion = fecha_creacion;
    }
}


