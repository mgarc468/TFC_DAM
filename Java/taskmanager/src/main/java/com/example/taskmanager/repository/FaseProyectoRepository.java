package com.example.taskmanager.repository;

import com.example.taskmanager.model.Fase_Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para acceder y gestionar datos de la entidad {@link Fase_Proyecto}.
 * Extiende {@link JpaRepository} para aprovechar funcionalidades CRUD predeterminadas.
 */
public interface FaseProyectoRepository extends JpaRepository<Fase_Proyecto, Integer> {

    /**
     * Obtiene una lista de nombres distintos de fases de proyecto.
     *
     * @return lista de nombres de fases
     */
    @Query("SELECT DISTINCT f.nombre FROM Fase_Proyecto f ORDER BY f.nombre")
    List<String> findDistinctNombres();

    /**
     * Busca una fase de proyecto por su ID utilizando una consulta.
     *
     * @param id identificador de la fase
     * @return un {@link Optional} que contiene la fase si existe, o vacío si no se encuentra
     */
    @Query("FROM Fase_Proyecto o WHERE o.id = :id")
    Optional<Fase_Proyecto> findById(@Param("id") int id);
}
