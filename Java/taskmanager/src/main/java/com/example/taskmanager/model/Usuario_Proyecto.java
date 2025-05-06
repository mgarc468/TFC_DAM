package com.example.taskmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Entidad intermedia que representa la relación muchos-a-muchos entre usuarios y proyectos.
 * Se utiliza como tabla de unión.
 */
@Getter
@Setter
@Entity
@Table(name = "usuarios_proyectos")
public class Usuario_Proyecto implements Serializable {

    /**
     * Identificador único de la relación usuario-proyecto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Usuario asociado al proyecto.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Proyecto al que está asociado el usuario.
     */
    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    // -------------------- Constructores --------------------

    /**
     * Constructor por defecto.
     */
    public Usuario_Proyecto() {
    }

    /**
     * Constructor para crear la relación entre un usuario y un proyecto.
     *
     * @param usuario  el usuario asociado
     * @param proyecto el proyecto asociado
     */
    public Usuario_Proyecto(Usuario usuario, Proyecto proyecto) {
        this.usuario = usuario;
        this.proyecto = proyecto;
    }

    /**
     * Constructor completo con ID (por ejemplo, para usos administrativos o pruebas).
     *
     * @param id       ID de la relación
     * @param usuario  usuario asociado
     * @param proyecto proyecto asociado
     */
    public Usuario_Proyecto(int id, Usuario usuario, Proyecto proyecto) {
        this.id = id;
        this.usuario = usuario;
        this.proyecto = proyecto;
    }

    // -------------------- Métodos getters y setters --------------------

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
