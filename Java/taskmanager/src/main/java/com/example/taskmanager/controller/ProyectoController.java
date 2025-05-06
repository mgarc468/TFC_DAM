package com.example.taskmanager.controller;

import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.repository.UsuarioRepository;
import com.example.taskmanager.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar operaciones relacionadas con proyectos.
 */
@RestController
@RequestMapping("/proyecto")
public class ProyectoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProyectoService proyectoService;

    /**
     * Endpoint de prueba para mostrar un mensaje de error.
     *
     * @return mensaje de error
     */
    @GetMapping("/error")
    public String getError() {
        return "Error en la app";
    }

    /**
     * Crea un nuevo proyecto asociado a un usuario.
     *
     * @param proyecto  objeto Proyecto recibido en el cuerpo de la solicitud
     * @param usuarioId ID del usuario que crea el proyecto
     * @return mensaje de éxito
     */
    @PostMapping("/add")
    public ResponseEntity<?> addProyecto(@RequestBody Proyecto proyecto, @RequestParam int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        proyecto.setCreadoPor(usuario);
        proyecto.setFecha_creacion(new Date());

        proyectoService.agregarProyecto(proyecto);

        return ResponseEntity.ok(Map.of("mensaje", "Proyecto creado correctamente"));
    }

    /**
     * Lista todos los proyectos existentes.
     *
     * @return lista de proyectos
     */
    @GetMapping("/dashboard")
    public ResponseEntity<List<Proyecto>> getAllProyectos() {
        return new ResponseEntity<>(proyectoService.listarProyectos(), HttpStatus.OK);
    }

    /**
     * Actualiza los datos de un proyecto existente.
     *
     * @param id               ID del proyecto a actualizar
     * @param datosActualizados objeto Proyecto con los datos actualizados
     * @return proyecto actualizado
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Proyecto> editarProyecto(@PathVariable int id, @RequestBody Proyecto datosActualizados) {
        Proyecto actualizado = proyectoService.editarProyecto(id, datosActualizados);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina un proyecto a partir de su ID.
     *
     * @param id ID del proyecto a eliminar
     * @return respuesta HTTP sin contenido
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable int id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Asigna un usuario a un proyecto.
     *
     * @param body mapa con las claves "usuarioId" y "proyectoId"
     * @return mensaje de éxito
     */
    @PostMapping("/asignarUsuario")
    public ResponseEntity<String> asignarUsuarioAProyecto(@RequestBody Map<String, Integer> body) {
        int usuarioId = body.get("usuarioId");
        int proyectoId = body.get("proyectoId");

        proyectoService.asignarUsuarioAProyecto(proyectoId, usuarioId);

        return ResponseEntity.ok("Usuario asignado correctamente al proyecto");
    }

    /**
     * Elimina un usuario de un proyecto.
     *
     * @param body mapa con las claves "usuarioId" y "proyectoId"
     * @return mensaje de éxito
     */
    @PostMapping("/eliminarUsuario")
    public ResponseEntity<String> eliminarUsuarioDeProyecto(@RequestBody Map<String, Integer> body) {
        int usuarioId = body.get("usuarioId");
        int proyectoId = body.get("proyectoId");

        proyectoService.eliminarUsuarioDeProyecto(proyectoId, usuarioId);

        return ResponseEntity.ok("Usuario eliminado correctamente del proyecto");
    }
}
