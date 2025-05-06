package com.example.taskmanager.service;

import com.example.taskmanager.model.Rol;
import com.example.taskmanager.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio {@link RolService} que gestiona las operaciones relacionadas con roles de usuario.
 */
@Service
public class RolServiceImp implements RolService {

    @Autowired
    private RolRepository rolRepository;

    /**
     * Devuelve todos los roles que existen en base de datos.
     *
     * @return lista de roles
     */
    @Override
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    /**
     * Busca un rol por su ID.
     *
     * @param id ID del rol
     * @return el rol si existe, o {@code null} si no se encuentra
     */
    @Override
    public Rol buscarPorId(int id) {
        return rolRepository.findById(id).orElse(null);
    }

    /**
     * Busca múltiples roles a partir de una lista de IDs.
     *
     * @param ids lista de IDs
     * @return lista de roles encontrados
     */
    @Override
    public List<Rol> buscarPorIds(List<Integer> ids) {
        return rolRepository.findAllById(ids);
    }
}
