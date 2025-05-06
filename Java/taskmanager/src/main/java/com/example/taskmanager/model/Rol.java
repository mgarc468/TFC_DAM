package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Entidad que representa un rol dentro del sistema.
 * Cada rol puede estar asociado a varios usuarios.
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Rol implements Serializable {

    /**
     * Identificador único del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nombre del rol.
     */
    @Column
    private String nombre;

    /**
     * Lista de usuarios que tienen este rol.
     * Se omite en la serialización JSON para evitar recursión infinita.
     */
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<Usuario> usuarios;

    // -------------------- Constructores --------------------

    /**
     * Constructor vacío.
     */
    public Rol() {
    }

    /**
     * Constructor completo.
     */
    public Rol(int id, String nombre, List<Usuario> usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.usuarios = usuarios;
    }

    /**
     * Constructor con el nombre y los usuarios.
     */
    public Rol(String nombre, List<Usuario> usuarios) {
        this.nombre = nombre;
        this.usuarios = usuarios;
    }

    // -------------------- Métodos getters y setters --------------------

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
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

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
