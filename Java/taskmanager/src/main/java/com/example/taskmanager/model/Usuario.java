package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter


@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String password;

    @Column(updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_creacion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "usuarios")
    @JsonIgnore
    private List<Proyecto> proyectos;

    public Usuario() {
    }

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Usuario(String nombre, String email, String password, Date fecha_creacion) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fecha_creacion = fecha_creacion;
    }

    public Usuario(int id, String nombre, String email, String password, Date fecha_creacion, List<Rol> roles, List<Proyecto> proyectos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fecha_creacion = fecha_creacion;
        this.roles = roles;
        this.proyectos = proyectos;
    }

    public Usuario(String nombre, String email, String password, Date fecha_creacion, List<Rol> roles, List<Proyecto> proyectos) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fecha_creacion = fecha_creacion;
        this.roles = roles;
        this.proyectos = proyectos;
    }

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
