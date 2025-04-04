package com.example.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter

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

    @ManyToMany
    @JoinTable(
            name = "usuarios_proyectos",
            joinColumns = @JoinColumn(name = "proyecto_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios = new ArrayList<>();

    public Proyecto() {
    }

    public Proyecto(int id, String nombre, String descripcion, float presupuesto_estimado, float coste_interno, float coste_externo, float coste_total, String fase_actual, int creado_por, Date fecha_creacion, List<Usuario> usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presupuesto_estimado = presupuesto_estimado;
        this.coste_interno = coste_interno;
        this.coste_externo = coste_externo;
        this.coste_total = coste_total;
        this.fase_actual = fase_actual;
        this.creado_por = creado_por;
        this.fecha_creacion = fecha_creacion;
        this.usuarios = usuarios;
    }

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

    public Proyecto(String nombre, String descripcion, float presupuesto_estimado, float coste_interno, float coste_externo, float coste_total, String fase_actual, int creado_por, Date fecha_creacion, List<Usuario> usuarios) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presupuesto_estimado = presupuesto_estimado;
        this.coste_interno = coste_interno;
        this.coste_externo = coste_externo;
        this.coste_total = coste_total;
        this.fase_actual = fase_actual;
        this.creado_por = creado_por;
        this.fecha_creacion = fecha_creacion;
        this.usuarios = usuarios;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getPresupuesto_estimado() {
        return presupuesto_estimado;
    }

    public float getCoste_interno() {
        return coste_interno;
    }

    public float getCoste_externo() {
        return coste_externo;
    }

    public float getCoste_total() {
        return coste_total;
    }

    public String getFase_actual() {
        return fase_actual;
    }

    public int getCreado_por() {
        return creado_por;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPresupuesto_estimado(float presupuesto_estimado) {
        this.presupuesto_estimado = presupuesto_estimado;
    }

    public void setCoste_interno(float coste_interno) {
        this.coste_interno = coste_interno;
    }

    public void setCoste_externo(float coste_externo) {
        this.coste_externo = coste_externo;
    }

    public void setCoste_total(float coste_total) {
        this.coste_total = coste_total;
    }

    public void setFase_actual(String fase_actual) {
        this.fase_actual = fase_actual;
    }

    public void setCreado_por(int creado_por) {
        this.creado_por = creado_por;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}


