package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


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

    @ManyToOne
    @JoinColumn(name = "creado_por", referencedColumnName = "id")
    private Usuario creadoPor;

    @Column
    private Date fecha_creacion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuarios_proyectos",
            joinColumns = @JoinColumn(name = "proyecto_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Fase_Proyecto> fases = new ArrayList<>();

    public Proyecto() {
    }

    public Proyecto(int id, String nombre, String descripcion, float presupuesto_estimado, float coste_interno, float coste_externo, float coste_total, String fase_actual, Usuario creadoPor, Date fecha_creacion, List<Usuario> usuarios, List<Fase_Proyecto> fases) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presupuesto_estimado = presupuesto_estimado;
        this.coste_interno = coste_interno;
        this.coste_externo = coste_externo;
        this.coste_total = coste_total;
        this.fase_actual = fase_actual;
        this.creadoPor = creadoPor;
        this.fecha_creacion = fecha_creacion;
        this.usuarios = usuarios;
        this.fases = fases;
    }

    public Proyecto(String nombre, String descripcion, float presupuesto_estimado, float coste_interno, float coste_externo, float coste_total, String fase_actual, Usuario creadoPor, Date fecha_creacion, List<Usuario> usuarios, List<Fase_Proyecto> fases) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presupuesto_estimado = presupuesto_estimado;
        this.coste_interno = coste_interno;
        this.coste_externo = coste_externo;
        this.coste_total = coste_total;
        this.fase_actual = fase_actual;
        this.creadoPor = creadoPor;
        this.fecha_creacion = fecha_creacion;
        this.usuarios = usuarios;
        this.fases = fases;
    }

    public Proyecto(String nombre, String descripcion, float presupuesto_estimado, float coste_interno, float coste_externo, float coste_total, String fase_actual, Usuario creadoPor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presupuesto_estimado = presupuesto_estimado;
        this.coste_interno = coste_interno;
        this.coste_externo = coste_externo;
        this.coste_total = coste_total;
        this.fase_actual = fase_actual;
        this.creadoPor = creadoPor;
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

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Fase_Proyecto> getFases() {
        return fases;
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

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setFases(List<Fase_Proyecto> fases) {
        this.fases = fases;
    }
}


