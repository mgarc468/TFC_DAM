package com.example.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

@Entity
@Table(name="fases_proyectos")
public class Fase_Proyecto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    @Column
    private String nombre;

    @Column
    private int duracion_dias;

    public Fase_Proyecto() {
    }

    public Fase_Proyecto(int id, Proyecto proyecto, String nombre, int duracion_dias) {
        this.id = id;
        this.proyecto = proyecto;
        this.nombre = nombre;
        this.duracion_dias = duracion_dias;
    }

    public Fase_Proyecto(Proyecto proyecto, String nombre, int duracion_dias) {
        this.proyecto = proyecto;
        this.nombre = nombre;
        this.duracion_dias = duracion_dias;
    }

    public int getId() {
        return id;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion_dias() {
        return duracion_dias;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDuracion_dias(int duracion_dias) {
        this.duracion_dias = duracion_dias;
    }
}



