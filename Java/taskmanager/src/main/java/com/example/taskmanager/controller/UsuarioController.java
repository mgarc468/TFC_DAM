package com.example.taskmanager.controller;

import com.example.taskmanager.model.Proyecto;
import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.service.EmailService;
import com.example.taskmanager.service.ProyectoService;
import com.example.taskmanager.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/error")
    public String getError(){
        return "Error en la app";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Usuario>> getAllUsuarios(){
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUsuario(@RequestBody Usuario usuario) {
        // Verificar si el correo electrónico ya existe
        if (usuarioService.existeEmail(usuario.getEmail())) {
            // Devolver un mensaje de error en formato JSON
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "El correo electrónico ya está registrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Si el usuario no existe, lo agregamos
        usuarioService.agregarUsuario(usuario);
        return ResponseEntity.ok("Usuario guardado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario loginRequest) {
        boolean loginCorrecto = usuarioService.validarCredenciales(
                loginRequest.getEmail(), loginRequest.getPassword()
        );

        if (loginCorrecto) {
            return ResponseEntity.ok("Login correcto");
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }

    @Autowired
    private EmailService emailService;

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
            e.printStackTrace(); // 👈 esto muestra el error en la consola
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }


}
