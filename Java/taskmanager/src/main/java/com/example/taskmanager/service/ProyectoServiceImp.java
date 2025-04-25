package com.example.taskmanager.service;

import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.repository.ProyectoRepository;
import com.example.taskmanager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoServiceImp implements ProyectoService{

    @Autowired
    private ProyectoRepository proyectoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Proyecto agregarProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    @Override
    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAllWithFases();
    }

    @Override
    public Proyecto editarProyecto(int id, Proyecto datosActualizados) {
        Proyecto existente = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        existente.setNombre(datosActualizados.getNombre());
        existente.setDescripcion(datosActualizados.getDescripcion());
        existente.setPresupuesto_estimado(datosActualizados.getPresupuesto_estimado());
        existente.setCoste_interno(datosActualizados.getCoste_interno());
        existente.setCoste_externo(datosActualizados.getCoste_externo());
        existente.setCoste_total(datosActualizados.getCoste_total());
        existente.setFase_actual(datosActualizados.getFase_actual());
        //existente.setCreadoPor(datosActualizados.getCreadoPor());

        return proyectoRepository.save(existente);
    }

    @Override
    public void eliminarProyecto(int id) {
        proyectoRepository.deleteById(id);
    }

    @Override
    public void asignarUsuarioAProyecto(int proyectoId, int usuarioId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        proyecto.getUsuarios().add(usuario);
        proyectoRepository.save(proyecto);
    }

    @Override
    public void eliminarUsuarioDeProyecto(int proyectoId, int usuarioId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        proyecto.getUsuarios().remove(usuario);
        proyectoRepository.save(proyecto);
    }


}
