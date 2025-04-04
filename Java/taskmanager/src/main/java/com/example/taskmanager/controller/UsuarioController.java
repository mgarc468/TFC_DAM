package com.example.taskmanager.controller;

import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.service.ProyectoService;
import com.example.taskmanager.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("error")
    public String getError(){
        return "Error en la app";
    }

    @PostMapping("add")
    public String addProyecto(@RequestBody Usuario usuario){
        usuarioService.agregarUsuario(usuario);
        return "Usuario guardado correctamente";
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Usuario>> getAllUsuarios(){
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

}
