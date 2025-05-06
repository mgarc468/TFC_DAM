package com.example.taskmanager.repository;

import com.example.taskmanager.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repositorio para gestionar operaciones de acceso a datos de la entidad {@link Proyecto}.
 * Hereda operaciones CRUD básicas de {@link JpaRepository}.
 */
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    /**
     * Obtiene todos los proyectos junto con sus fases asociadas utilizando una consulta.
     *
     * @return lista de proyectos con sus fases cargadas
     */
    @Query("SELECT DISTINCT p FROM Proyecto p LEFT JOIN FETCH p.fases")
    List<Proyecto> findAllWithFases();
}
