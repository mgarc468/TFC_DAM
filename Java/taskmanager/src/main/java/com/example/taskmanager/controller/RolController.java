package com.example.taskmanager.controller;

import com.example.taskmanager.model.Rol;
import com.example.taskmanager.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con los roles.
 */
@RestController
@RequestMapping("rol")
public class RolController {

    @Autowired
    private RolService rolService;

    /**
     * Endpoint de prueba para retornar un mensaje de error genérico.
     *
     * @return mensaje de error
     */
    @GetMapping("error")
    public String getError() {
        return "Error en la app";
    }

    /**
     * Obtiene una lista de todos los roles registrados en el sistema.
     *
     * @return lista de objetos {@link Rol}
     */
    @GetMapping("/getAll")
    public List<Rol> getAllRoles() {
        return rolService.listarTodos();
    }

}
