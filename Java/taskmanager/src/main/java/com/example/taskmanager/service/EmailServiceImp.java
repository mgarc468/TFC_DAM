package com.example.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio {@link EmailService} que utiliza {@link JavaMailSender}
 * para enviar correos electrónicos.
 */
@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo electrónico.
     *
     * @param destino   dirección de correo del destinatario
     * @param asunto    asunto del mensaje
     * @param contenido cuerpo del mensaje en texto plano
     */
    @Override
    public void enviarCorreo(String destino, String asunto, String contenido) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destino);
        mensaje.setSubject(asunto);
        mensaje.setText(contenido);
        mensaje.setFrom("miguelangel.garcia230@comunidadunir.net"); // Dirección configurada en application.properties

        mailSender.send(mensaje);
    }
}
