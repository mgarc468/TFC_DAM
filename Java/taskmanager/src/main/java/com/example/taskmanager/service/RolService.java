package com.example.taskmanager.service;

import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.model.Rol;

import java.util.List;

public interface RolService {

    List<Rol> listarTodos();
    Rol buscarPorId(int id);
    List<Rol> buscarPorIds(List<Integer> ids);
}
