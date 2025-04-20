package com.example.taskmanager.service;

import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public boolean validarCredenciales(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        return usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password);
    }

    @Override
    public boolean existeEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        return usuarioOpt.isPresent();
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public String generarPasswordTemporal() {
        return UUID.randomUUID().toString().substring(0, 8); // Ejemplo: "a1b2c3d4"
    }

}
