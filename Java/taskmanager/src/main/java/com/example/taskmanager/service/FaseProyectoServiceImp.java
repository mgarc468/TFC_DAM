package com.example.taskmanager.service;

import com.example.taskmanager.repository.FaseProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaseProyectoServiceImp implements FaseProyectoService{

    @Autowired
    private FaseProyectoRepository faseProyectoRepository;

    @Override
    public void eliminarFaseProyecto(int id) {
        faseProyectoRepository.deleteById(id);
    }
}
