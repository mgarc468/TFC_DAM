package com.example.taskmanager.service;

import com.example.taskmanager.model.Proyecto;

import java.util.List;

/**
 * Servicio que define las operaciones relacionadas con la gestión de proyectos.
 */
public interface ProyectoService {

    /**
     * Crea y guarda un nuevo proyecto.
     *
     * @param proyecto el proyecto a agregar
     * @return el proyecto guardado con su ID asignado
     */
    Proyecto agregarProyecto(Proyecto proyecto);

    /**
     * Obtiene una lista de todos los proyectos registrados.
     *
     * @return lista de proyectos
     */
    List<Proyecto> listarProyectos();

    /**
     * Lista los proyectos existentes.
     *
     * @param id                ID del proyecto a actualizar
     * @param datosActualizados objeto con los nuevos datos del proyecto
     * @return el proyecto actualizado
     */
    Proyecto editarProyecto(int id, Proyecto datosActualizados);

    /**
     * Elimina un proyecto por su ID.
     *
     * @param id ID del proyecto a eliminar
     */
    void eliminarProyecto(int id);

    /**
     * Asigna un usuario a un proyecto.
     *
     * @param proyectoId ID del proyecto
     * @param usuarioId  ID del usuario a asignar
     */
    void asignarUsuarioAProyecto(int proyectoId, int usuarioId);

    /**
     * Elimina un usuario de un proyecto.
     *
     * @param proyectoId ID del proyecto
     * @param usuarioId  ID del usuario a eliminar
     */
    void eliminarUsuarioDeProyecto(int proyectoId, int usuarioId);
}
