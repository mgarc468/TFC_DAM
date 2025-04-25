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

@RestController
@RequestMapping("/proyecto")
public class ProyectoController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/error")
    public String getError(){
        return "Error en la app";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProyecto(@RequestBody Proyecto proyecto, @RequestParam int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        proyecto.setCreadoPor(usuario);
        proyecto.setFecha_creacion(new Date());

        proyectoService.agregarProyecto(proyecto);

        return ResponseEntity.ok(Map.of("mensaje", "Proyecto creado correctamente"));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<Proyecto>> getAllProyectos() {
        return new ResponseEntity<>(proyectoService.listarProyectos(), HttpStatus.OK);
    }

    // Editar
    @PutMapping("/update/{id}")
    public ResponseEntity<Proyecto> editarProyecto(@PathVariable int id, @RequestBody Proyecto datosActualizados) {
        Proyecto actualizado = proyectoService.editarProyecto(id, datosActualizados);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable int id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/asignarUsuario")
    public ResponseEntity<String> asignarUsuarioAProyecto(@RequestBody Map<String, Integer> body) {
        int usuarioId = body.get("usuarioId");
        int proyectoId = body.get("proyectoId");

        proyectoService.asignarUsuarioAProyecto(proyectoId, usuarioId);

        return ResponseEntity.ok("Usuario asignado correctamente al proyecto");
    }

    @PostMapping("/eliminarUsuario")
    public ResponseEntity<String> eliminarUsuarioDeProyecto(@RequestBody Map<String, Integer> body) {
        int usuarioId = body.get("usuarioId");
        int proyectoId = body.get("proyectoId");

        proyectoService.eliminarUsuarioDeProyecto(proyectoId, usuarioId);

        return ResponseEntity.ok("Usuario eliminado correctamente del proyecto");
    }

}
