package com.example.taskmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Entidad intermedia que representa la relación muchos-a-muchos entre {@link Usuario} y {@link Rol}.
 * Se utiliza como tabla de unión.
 */
@Getter
@Setter
@Entity
@Table(name = "usuarios_roles")
public class Usuario_Rol implements Serializable {

    /**
     * Identificador único de la relación usuario-rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Usuario asociado al rol.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Rol asignado al usuario.
     */
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    // -------------------- Constructores --------------------

    /**
     * Constructor por defecto.
     */
    public Usuario_Rol() {
    }

    /**
     * Constructor para crear una relación entre un usuario y un rol.
     *
     * @param usuario el usuario asociado
     * @param rol     el rol asignado
     */
    public Usuario_Rol(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
    }

    /**
     * Constructor completo con ID de la relación.
     *
     * @param id      ID de la relación
     * @param usuario el usuario asociado
     * @param rol     el rol asignado
     */
    public Usuario_Rol(int id, Usuario usuario, Rol rol) {
        this.id = id;
        this.usuario = usuario;
        this.rol = rol;
    }

    // -------------------- Métodos getters y setters --------------------

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
