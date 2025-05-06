package com.example.taskmanager.controller;

import com.example.taskmanager.model.Fase_Proyecto;
import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.repository.FaseProyectoRepository;
import com.example.taskmanager.repository.ProyectoRepository;
import com.example.taskmanager.service.FaseProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar las fases de los proyectos.
 */
@RestController
@RequestMapping("/fases")
public class FaseProyectoController {

    @Autowired
    private FaseProyectoRepository faseProyectoRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private FaseProyectoService faseproyectoService;

    /**
     * Obtiene una lista de nombres distintos de fases.
     *
     * @return lista de nombres de fases
     */
    @GetMapping("/nombres")
    public List<String> getNombresFases() {
        return faseProyectoRepository.findDistinctNombres();
    }

    /**
     * Actualiza una fase existente a partir de su ID y los datos proporcionados.
     *
     * @param id   ID de la fase a actualizar
     * @param body mapa con los campos a actualizar
     * @return respuesta HTTP con el estado de la operación
     */
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

    /**
     * Convierte un objeto a una fecha SQL si el formato es válido.
     *
     * @param fechaObj objeto con la fecha (String o similar)
     * @param campo    nombre del campo para debug
     * @return fecha en formato java.sql.Date o null si hay error
     */
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

    /**
     * Agrega una nueva fase a un proyecto existente.
     *
     * @param body mapa con los datos de la fase
     * @return respuesta HTTP con el estado de la operación
     */
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

    /**
     * Elimina una fase por su ID.
     *
     * @param id ID de la fase a eliminar
     * @return respuesta HTTP sin contenido
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarFase(@PathVariable int id) {
        faseproyectoService.eliminarFase(id);
        return ResponseEntity.noContent().build();
    }
}
