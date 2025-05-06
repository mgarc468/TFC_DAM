package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Entidad que representa una fase dentro de un proyecto.
 * Puede tener asociadas varias fases a un proyecto, cada una de ellas tiene fecha de inicio y fin.
 */
@Getter
@Setter
@Entity
@Table(name = "fases_proyecto")
public class Fase_Proyecto implements Serializable {

    /**
     * Identificador único de la fase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Proyecto al que pertenece esta fase.
     */
    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    @JsonBackReference
    private Proyecto proyecto;

    /**
     * Nombre de la fase.
     */
    @Column
    private String nombre;

    /**
     * Duración estimada de la fase en días.
     */
    @Column
    private int duracion_dias;

    /**
     * Fecha de inicio de la fase.
     */
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column
    private Date fecha_inicio;

    /**
     * Fecha de finalización de la fase.
     */
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column
    private Date fecha_fin;
    // -------------------- Constructores --------------------

    /**
     * Constructor sin nada.
     */
    public Fase_Proyecto() {
    }

    /**
     * Constructor completo.
     */
    public Fase_Proyecto(int id, Proyecto proyecto, String nombre, int duracion_dias, Date fecha_inicio, Date fecha_fin) {
        this.id = id;
        this.proyecto = proyecto;
        this.nombre = nombre;
        this.duracion_dias = duracion_dias;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    // -------------------- Métodos getters y setters --------------------

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

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
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

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }
}
