package com.example.taskmanager.repository;

import com.example.taskmanager.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    @Query("SELECT DISTINCT p FROM Proyecto p LEFT JOIN FETCH p.fases")
    List<Proyecto> findAllWithFases();
}
