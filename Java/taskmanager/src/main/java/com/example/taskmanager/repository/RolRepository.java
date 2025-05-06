package com.example.taskmanager.repository;

import com.example.taskmanager.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para acceder y gestionar datos de la entidad {@link Rol}.
 * Hereda las operaciones CRUD estándar de {@link JpaRepository}.
 */
public interface RolRepository extends JpaRepository<Rol, Integer> {

    /**
     * Busca un rol por su nombre.
     *
     * @param nombre el nombre del rol.
     * @return un {@link Optional} que contiene el rol si existe, o vacío si no se encuentra
     */
    Optional<Rol> findByNombre(String nombre);
}
