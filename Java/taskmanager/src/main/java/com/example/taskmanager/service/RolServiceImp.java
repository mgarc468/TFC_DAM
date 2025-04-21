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
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    @Override
    public Rol buscarPorId(int id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public List<Rol> buscarPorIds(List<Integer> ids) {
        return rolRepository.findAllById(ids);
    }
}
