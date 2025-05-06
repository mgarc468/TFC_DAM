package com.example.taskmanager.service;

import com.example.taskmanager.model.Rol;

import java.util.List;

/**
 * Servicio que define los roles en el sistema.
 */
public interface RolService {

    /**
     * Obtiene una lista de todos los roles disponibles en la base de datos.
     *
     * @return lista de objetos {@link Rol}
     */
    List<Rol> listarTodos();

    /**
     * Busca un rol específico por su ID.
     *
     * @param id el ID del rol a buscar
     * @return el objeto {@link Rol} correspondiente, si se encuentra
     */
    Rol buscarPorId(int id);

    /**
     * Busca múltiples roles a partir de una lista de IDs.
     *
     * @param ids lista de IDs de roles a buscar
     * @return lista de roles encontrados
     */
    List<Rol> buscarPorIds(List<Integer> ids);
}
