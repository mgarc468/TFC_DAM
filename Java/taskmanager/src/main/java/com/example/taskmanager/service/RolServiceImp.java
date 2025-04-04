package com.example.taskmanager.service;

import com.example.taskmanager.model.Rol;
import com.example.taskmanager.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImp implements RolService{

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Rol agregarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }
}
