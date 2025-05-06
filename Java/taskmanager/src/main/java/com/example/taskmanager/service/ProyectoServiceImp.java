package com.example.taskmanager.service;

import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.repository.ProyectoRepository;
import com.example.taskmanager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la interfaz {@link ProyectoService}.
 */
@Service
public class ProyectoServiceImp implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Guarda un nuevo proyecto.
     *
     * @param proyecto el proyecto a guardar
     * @return el proyecto guardado con ID asignado
     */
    @Override
    public Proyecto agregarProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    /**
     * Devuelve una lista de todos los proyectos junto con sus fases asociadas.
     *
     * @return lista de proyectos
     */
    @Override
    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAllWithFases();
    }

    /**
     * Edita un proyecto existente con nuevos datos.
     *
     * @param id                ID del proyecto a editar
     * @param datosActualizados objeto con los datos nuevos del proyecto
     * @return el proyecto actualizado
     */
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

        return proyectoRepository.save(existente);
    }

    /**
     * Elimina un proyecto por su ID.
     *
     * @param id ID del proyecto a eliminar
     */
    @Override
    public void eliminarProyecto(int id) {
        proyectoRepository.deleteById(id);
    }

    /**
     * Asigna un usuario a un proyecto.
     *
     * @param proyectoId ID del proyecto
     * @param usuarioId  ID del usuario
     */
    @Override
    public void asignarUsuarioAProyecto(int proyectoId, int usuarioId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        proyecto.getUsuarios().add(usuario);
        proyectoRepository.save(proyecto);
    }

    /**
     * Elimina un usuario previamente asignado a un proyecto.
     *
     * @param proyectoId ID del proyecto
     * @param usuarioId  ID del usuario
     */
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
