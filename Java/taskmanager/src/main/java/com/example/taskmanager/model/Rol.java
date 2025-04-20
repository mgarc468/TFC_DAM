package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter


@Entity
@Table(name="roles")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombre;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Rol> roles;

    public Rol() {
    }

    public Rol(String nombre, List<Rol> roles) {
        this.nombre = nombre;
        this.roles = roles;
    }

    public Rol(int id, String nombre, List<Rol> roles) {
        this.id = id;
        this.nombre = nombre;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
