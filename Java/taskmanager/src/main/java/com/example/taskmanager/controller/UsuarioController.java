package com.example.taskmanager.controller;

import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.service.EmailService;
import com.example.taskmanager.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/error")
    public String getError() {
        return "Error en la app";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getPassword().equals(password)) {
                usuario.setPassword(null); // 🛑 oculta la contraseña en la respuesta
                return ResponseEntity.ok(usuario);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }

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

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok("Usuario actualizado");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado");
    }

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
