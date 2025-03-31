package com.example.taskmanager.controller;

import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("proyecto")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("error")
    public String getError(){
        return "Error en la app";
    }

    @PostMapping("add")
    public String addProyecto(@RequestBody Proyecto proyecto){
        proyectoService.agregarProyecto(proyecto);
        return "Proyecto guardado correctamente";
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Proyecto>> getAllProyectos(){
        return new ResponseEntity<>(proyectoService.listarProyectos(), HttpStatus.OK);
    }
}
