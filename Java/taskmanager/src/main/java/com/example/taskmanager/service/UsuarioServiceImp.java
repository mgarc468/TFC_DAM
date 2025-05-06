package com.example.taskmanager.service;

import com.example.taskmanager.model.Rol;
import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.repository.RolRepository;
import com.example.taskmanager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación del servicio {@link UsuarioService} para gestionar operaciones relacionadas con usuarios
 */
@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario el usuario a agregar
     * @return el usuario guardado con ID asignado
     */
    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Obtiene una lista de todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Verifica si las credenciales del usuario son correctas.
     *
     * @param email    el email ingresado
     * @param password la contraseña ingresada
     * @return true si las credenciales son válidas, false si no
     */
    @Override
    public boolean validarCredenciales(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        return usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password);
    }

    /**
     * Verifica si un correo electrónico ya está registrado.
     *
     * @param email el correo a comprobar
     * @return true si existe, false si no
     */
    @Override
    public boolean existeEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    /**
     * Busca un usuario por su email.
     *
     * @param email dirección de correo del usuario
     * @return {@link Optional} que puede contener un usuario si se encuentra
     */
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Genera una contraseña temporal aleatoria de 12 caracteres.
     *
     * @return contraseña temporal generada
     */
    @Override
    public String generarPasswordTemporal() {
        return UUID.randomUUID().toString().substring(0, 12);
    }

    /**
     * Guarda un usuario (nuevo o actualizado).
     *
     * @param usuario el usuario a guardar
     * @return el usuario guardado
     */
    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar
     */
    @Override
    public void eliminarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Crea un nuevo usuario y le asigna un rol específico.
     *
     * @param usuario el usuario a crear
     * @param rolId   el ID del rol que se le asignará
     * @return el usuario creado con el rol asignado
     */
    @Override
    public Usuario crearUsuarioConRol(Usuario usuario, int rolId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + rolId));

        List<Rol> roles = new ArrayList<>();
        roles.add(rol);
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }
}
