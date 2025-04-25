package com.example.taskmanager.repository;

import com.example.taskmanager.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query("FROM Usuario u WHERE u.id = :id")
    Optional<Usuario> findById(@Param("id") int id);


}
