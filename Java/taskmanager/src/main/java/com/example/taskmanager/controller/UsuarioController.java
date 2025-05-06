package com.example.taskmanager.controller;

import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.service.EmailService;
import com.example.taskmanager.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controlador REST para la gestión de usuarios, incluyendo registro, login, recuperación de contraseña,
 * y operaciones CRUD básicas.
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    /**
     * Endpoint de prueba para mostrar un mensaje de error.
     *
     * @return mensaje de error genérico
     */
    @GetMapping("/error")
    public String getError() {
        return "Error en la app";
    }

    /**
     * Obtiene una lista de todos los usuarios registrados.
     *
     * @return lista de objetos {@link Usuario}
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    /**
     * Agrega un nuevo usuario al sistema si el correo no está registrado.
     *
     * @param usuario objeto {@link Usuario} con los datos a guardar
     * @return mensaje de éxito o error si el email ya existe
     */
    @PostMapping("/add")
    public ResponseEntity<?> addUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.existeEmail(usuario.getEmail())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "El correo electrónico ya está registrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        usuarioService.agregarUsuario(usuario);
        return ResponseEntity.ok("Usuario guardado correctamente");
    }

    /**
     * Realiza el inicio de sesión validando las credenciales proporcionadas.
     *
     * @param request mapa con "email" y "password"
     * @return el usuario autenticado o mensaje de error
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getPassword().equals(password)) {
                usuario.setPassword(null); // Oculta la contraseña antes de responder
                return ResponseEntity.ok(usuario);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }

    /**
     * Envía una nueva contraseña temporal al correo del usuario si existe en el sistema.
     *
     * @param body mapa con el campo "email"
     * @return mensaje indicando el estado del proceso de recuperación
     */
    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperarPassword(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);

            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                String nuevaPassword = usuarioService.generarPasswordTemporal();
                usuario.setPassword(nuevaPassword);
                usuarioService.agregarUsuario(usuario);

                emailService.enviarCorreo(
                        email,
                        "Recuperación de contraseña",
                        "Tu nueva contraseña temporal es: " + nuevaPassword
                );

                return ResponseEntity.ok("Correo enviado con la nueva contraseña.");
            }

            return ResponseEntity.status(404).body("Correo no registrado");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de un usuario existente según su ID.
     *
     * @param id      ID del usuario a actualizar
     * @param usuario datos actualizados del usuario
     * @return mensaje de éxito
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok("Usuario actualizado");
    }

    /**
     * Elimina un usuario del sistema por su ID.
     *
     * @param id ID del usuario a eliminar
     * @return mensaje de éxito
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado");
    }

    /**
     * Crea un nuevo usuario con un rol asociado.
     *
     * @param body mapa con los campos "nombre", "email", "password" y "rolId"
     * @return el usuario creado con su rol asignado
     */
    @PostMapping("/addWithRol")
    public ResponseEntity<Usuario> crearUsuarioConRol(@RequestBody Map<String, Object> body) {
        String nombre = (String) body.get("nombre");
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        int rolId = Integer.parseInt(body.get("rolId").toString());

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(password);

        Usuario usuarioCreado = usuarioService.crearUsuarioConRol(nuevoUsuario, rolId);
        return ResponseEntity.ok(usuarioCreado);
    }
}
