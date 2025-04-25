package com.example.taskmanager.controller;

import com.example.taskmanager.model.Fase_Proyecto;
import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.repository.FaseProyectoRepository;
import com.example.taskmanager.repository.ProyectoRepository;
import com.example.taskmanager.service.FaseProyectoService;
import com.example.taskmanager.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fases")
public class FaseProyectoController {

    @Autowired
    private FaseProyectoRepository faseProyectoRepository;
    @Autowired
    private ProyectoRepository proyectoRepository;
    @Autowired
    private FaseProyectoService faseproyectoService;

    @GetMapping("/nombres")
    public List<String> getNombresFases() {
        return faseProyectoRepository.findDistinctNombres();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarFase(@PathVariable int id, @RequestBody Map<String, Object> body) {
        try {
            System.out.println("Body recibido para actualización:");
            body.forEach((k, v) -> System.out.println(k + ": " + v));

            Fase_Proyecto fase = faseProyectoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Fase no encontrada"));

            if (body.containsKey("nombre")) {
                fase.setNombre((String) body.get("nombre"));
            }

            if (body.containsKey("duracion_dias")) {
                fase.setDuracion_dias((int) body.get("duracion_dias"));
            }

            if (body.containsKey("proyecto_id")) {
                int proyectoId = (int) body.get("proyecto_id");
                Proyecto proyecto = proyectoRepository.findById(proyectoId)
                        .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
                fase.setProyecto(proyecto);
            }

            fase.setFecha_inicio(parseFecha(body.get("fecha_inicio"), "fecha_inicio"));
            fase.setFecha_fin(parseFecha(body.get("fecha_fin"), "fecha_fin"));

            faseProyectoRepository.save(fase);
            return ResponseEntity.ok("Fase actualizada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al actualizar la fase: " + e.getMessage());
        }
    }

    // Utilidad para convertir diferentes formatos de fecha
    private java.sql.Date parseFecha(Object fechaObj, String campo) {
        if (fechaObj == null) return null;

        try {
            String fechaStr = fechaObj.toString();
            System.out.println("Campo " + campo + " recibido: " + fechaStr);

            if (fechaStr.length() >= 10) {
                LocalDate localDate = LocalDate.parse(fechaStr.substring(0, 10));
                return java.sql.Date.valueOf(localDate);
            }
        } catch (Exception e) {
            System.out.println("Error al parsear " + campo + ": " + e.getMessage());
        }

        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFase(@RequestBody Map<String, Object> body) {
        try {
            int proyectoId = (int) body.get("proyecto_id");

            Proyecto proyecto = proyectoRepository.findById(proyectoId)
                    .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

            Fase_Proyecto fase = new Fase_Proyecto();
            fase.setProyecto(proyecto);
            fase.setNombre((String) body.get("nombre"));
            fase.setDuracion_dias((int) body.get("duracion_dias"));
            fase.setFecha_inicio(java.sql.Date.valueOf((String) body.get("fecha_inicio")));
            fase.setFecha_fin(java.sql.Date.valueOf((String) body.get("fecha_fin")));

            faseProyectoRepository.save(fase);

            return ResponseEntity.ok("Fase guardada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al guardar la fase: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarFase(@PathVariable int id) {
        faseproyectoService.eliminarFase(id);
        return ResponseEntity.noContent().build();
    }

}
