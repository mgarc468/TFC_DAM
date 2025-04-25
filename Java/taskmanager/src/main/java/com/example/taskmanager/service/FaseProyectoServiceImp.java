package com.example.taskmanager.service;

import com.example.taskmanager.model.Fase_Proyecto;
import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.repository.FaseProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaseProyectoServiceImp implements FaseProyectoService{

    @Autowired
    private FaseProyectoRepository faseProyectoRepository;

    @Override
    public void eliminarFase(int id) {
        Fase_Proyecto fase = faseProyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fase no encontrada con id: " + id));

        Proyecto proyecto = fase.getProyecto();
        if (proyecto != null) {
            proyecto.getFases().remove(fase); // <<< Muy importante
        }

        faseProyectoRepository.delete(fase);
    }
}
