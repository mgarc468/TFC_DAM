package com.example.taskmanager.repository;

import com.example.taskmanager.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repositorio para gestionar operaciones de acceso a datos de la entidad {@link Usuario}.
 * Extiende {@link JpaRepository} para obtener funcionalidades CRUD estándar.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email el correo electrónico del usuario
     * @return un {@link Optional} que contiene el usuario si existe, o vacío si no se encuentra
     */
    @Query("FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    /**
     * Busca un usuario por su ID utilizando una consulta.
     *
     * @param id el identificador del usuario
     * @return un {@link Optional} con el usuario correspondiente, si existe
     */
    @Query("FROM Usuario u WHERE u.id = :id")
    Optional<Usuario> findById(@Param("id") int id);
}
