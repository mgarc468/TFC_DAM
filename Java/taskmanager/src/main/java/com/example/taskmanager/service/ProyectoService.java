package com.example.taskmanager.service;

import com.example.taskmanager.model.Proyecto;

import java.util.List;

public interface ProyectoService {

    Proyecto agregarProyecto(Proyecto proyecto);
    List<Proyecto> listarProyectos();
    Proyecto editarProyecto(int id, Proyecto datosActualizados);
    void eliminarProyecto(int id);
    void asignarUsuarioAProyecto(int proyectoId, int usuarioId);
    void eliminarUsuarioDeProyecto(int proyectoId, int usuarioId);
}
