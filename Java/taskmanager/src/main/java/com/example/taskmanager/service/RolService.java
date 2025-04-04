package com.example.taskmanager.service;

import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.model.Rol;

import java.util.List;

public interface RolService {

    Rol agregarRol(Rol rol);
    List<Rol> listarRoles();

}
