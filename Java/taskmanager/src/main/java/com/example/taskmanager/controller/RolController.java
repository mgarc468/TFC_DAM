package com.example.taskmanager.controller;

import com.example.taskmanager.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rol")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("error")
    public String getError(){
        return "Error en la app";
    }

}
