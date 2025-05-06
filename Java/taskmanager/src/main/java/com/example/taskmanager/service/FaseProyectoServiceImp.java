package com.example.taskmanager.service;

import com.example.taskmanager.model.Fase_Proyecto;
import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.repository.FaseProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio {@link FaseProyectoService} que gestiona operaciones
 * relacionadas con fases de proyectos.
 */
@Service
public class FaseProyectoServiceImp implements FaseProyectoService {

    @Autowired
    private FaseProyectoRepository faseProyectoRepository;

    /**
     * Elimina una fase de proyecto dado su ID.
     *
     * @param id el ID de la fase a eliminar
     * @throws RuntimeException si no se encuentra la fase con el ID proporcionado
     */
    @Override
    public void eliminarFase(int id) {
        Fase_Proyecto fase = faseProyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fase no encontrada con id: " + id));

        Proyecto proyecto = fase.getProyecto();
        if (proyecto != null) {
            proyecto.getFases().remove(fase); // 🔁 Mantiene la integridad de la relación bidireccional
        }

        faseProyectoRepository.delete(fase);
    }
}
