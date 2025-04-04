package com.example.taskmanager.service;

import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoServiceImp implements ProyectoService{

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    public Proyecto agregarProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    @Override
    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    @Override
    public List<Proyecto> buscarProyectoPorId(int id) {
        return List.of();
    }
}
