package com.example.taskmanager.controller;

import com.example.taskmanager.model.Rol;
import com.example.taskmanager.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rol")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("error")
    public String getError(){
        return "Error en la app";
    }

    @PostMapping("add")
    public String addProyecto(@RequestBody Rol rol){
        rolService.agregarRol(rol);
        return "Rol guardado correctamente";
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Rol>> getAllRoles(){
        return new ResponseEntity<>(rolService.listarRoles(), HttpStatus.OK);
    }
}
