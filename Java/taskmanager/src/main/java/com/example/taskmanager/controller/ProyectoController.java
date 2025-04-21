package com.example.taskmanager.controller;

import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/proyecto")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/error")
    public String getError(){
        return "Error en la app";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProyecto(@RequestBody Proyecto proyecto){
        proyectoService.agregarProyecto(proyecto);
        return ResponseEntity.ok(Map.of("mensaje", "Proyecto guardado correctamente"));
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
}
