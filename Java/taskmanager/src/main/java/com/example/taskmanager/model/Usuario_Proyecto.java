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
@Table(name="usuarios_proyectos")
public class Usuario_Proyecto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    public Usuario_Proyecto(Usuario usuario, Proyecto proyecto) {
        this.usuario = usuario;
        this.proyecto = proyecto;
    }

    public Usuario_Proyecto(int id, Usuario usuario, Proyecto proyecto) {
        this.id = id;
        this.usuario = usuario;
        this.proyecto = proyecto;
    }

    public Usuario_Proyecto() {
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
