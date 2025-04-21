package com.example.taskmanager.repository;

import com.example.taskmanager.model.Fase_Proyecto;
import com.example.taskmanager.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FaseProyectoRepository extends JpaRepository<Fase_Proyecto, Integer> {

    @Query("SELECT DISTINCT f.nombre FROM Fase_Proyecto f ORDER BY f.nombre")
    List<String> findDistinctNombres();

    @Query("FROM Fase_Proyecto o WHERE o.id = :id")
    Optional<Fase_Proyecto> findById(@Param("id") int id);
}
