package com.example.taskmanager.service;

import com.example.taskmanager.model.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario agregarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();

}
