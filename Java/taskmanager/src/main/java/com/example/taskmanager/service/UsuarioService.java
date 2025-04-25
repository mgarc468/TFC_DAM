package com.example.taskmanager.service;

import com.example.taskmanager.model.Usuario;


import java.util.List;
import java.util.Optional;


public interface UsuarioService {

    Usuario agregarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    boolean validarCredenciales(String email, String password);
    boolean existeEmail(String email);
    Optional<Usuario> buscarPorEmail(String email);
    String generarPasswordTemporal();
    Usuario guardarUsuario(Usuario usuario);
    void eliminarUsuario(int id);
    Usuario crearUsuarioConRol(Usuario usuario, int rolId);
}
