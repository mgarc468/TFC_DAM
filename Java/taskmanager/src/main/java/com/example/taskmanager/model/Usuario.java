package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entidad que representa a un usuario dentro del sistema.
 * Cada usuario puede tener múltiples roles y estar asociado a varios proyectos.
 */
@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nombre completo del usuario.
     */
    @Column
    private String nombre;

    /**
     * Dirección de correo electrónico del usuario. Debe ser única y no nula.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Contraseña del usuario.
     */
    @Column
    private String password;

    /**
     * Fecha de creación del registro. Este campo es automático y no debe ser modificado manualmente.
     */
    @Column(updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_creacion;

    /**
     * Lista de roles asociados al usuario.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles = new ArrayList<>();

    /**
     * Lista de proyectos en los que el usuario está involucrado.
     * Se ignora en la serialización JSON para evitar recursión infinita.
     */
    @ManyToMany(mappedBy = "usuarios")
    @JsonIgnore
    private List<Proyecto> proyectos;

    // -------------------- Constructores --------------------

    /**
     * Constructor vacío.
     */
    public Usuario() {
    }

    /**
     * Constructor básico con nombre, email y contraseña.
     */
    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor con fecha de creación incluida.
     */
    public Usuario(String nombre, String email, String password, Date fecha_creacion) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fecha_creacion = fecha_creacion;
    }

    /**
     * Constructor completo.
     */
    public Usuario(int id, String nombre, String email, String password, Date fecha_creacion, List<Rol> roles, List<Proyecto> proyectos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fecha_creacion = fecha_creacion;
        this.roles = roles;
        this.proyectos = proyectos;
    }

    /**
     * Constructor completo sin ID.
     */
    public Usuario(String nombre, String email, String password, Date fecha_creacion, List<Rol> roles, List<Proyecto> proyectos) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fecha_creacion = fecha_creacion;
        this.roles = roles;
        this.proyectos = proyectos;
    }

    // -------------------- Métodos getters y setters --------------------

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

}
