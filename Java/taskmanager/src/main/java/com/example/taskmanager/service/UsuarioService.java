package com.example.taskmanager.service;

import com.example.taskmanager.model.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que define las operaciones relacionadas con la gestión de usuarios en el sistema.
 */
public interface UsuarioService {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario el usuario a agregar
     * @return el usuario guardado con su ID asignado
     */
    Usuario agregarUsuario(Usuario usuario);

    /**
     * Obtiene la lista de todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    List<Usuario> listarUsuarios();

    /**
     * Verifica si las credenciales proporcionadas son válidas.
     *
     * @param email    email del usuario
     * @param password contraseña del usuario
     * @return true si las credenciales son correctas, false en caso contrario
     */
    boolean validarCredenciales(String email, String password);

    /**
     * Verifica si un correo electrónico ya está registrado en el sistema.
     *
     * @param email el correo a verificar
     * @return true si el email existe, false si no
     */
    boolean existeEmail(String email);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email email del usuario
     * @return un {@link Optional} que contiene el usuario si existe, o vacío si no
     */
    Optional<Usuario> buscarPorEmail(String email);

    /**
     * Genera una contraseña nueva.
     *
     * @return la contraseña nueva generada
     */
    String generarPasswordTemporal();

    /**
     * Guarda o actualiza un usuario existente.
     *
     * @param usuario el usuario a guardar
     * @return el usuario actualizado
     */
    Usuario guardarUsuario(Usuario usuario);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar
     */
    void eliminarUsuario(int id);

    /**
     * Crea un usuario y le asigna un rol específico.
     *
     * @param usuario el nuevo usuario a crear
     * @param rolId   ID del rol que se asignará
     * @return el usuario creado con su rol asignado
     */
    Usuario crearUsuarioConRol(Usuario usuario, int rolId);
}
