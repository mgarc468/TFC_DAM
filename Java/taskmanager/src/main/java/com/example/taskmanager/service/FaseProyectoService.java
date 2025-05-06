package com.example.taskmanager.service;

/**
 * Servicio que define operaciones relacionadas con las fases de un proyecto.
 */
public interface FaseProyectoService {

    /**
     * Elimina una fase de proyecto por su identificador.
     *
     * @param id el ID de la fase a eliminar
     */
    void eliminarFase(int id);
}
