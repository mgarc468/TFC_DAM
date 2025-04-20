package com.example.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreo(String destino, String asunto, String contenido) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destino);
        mensaje.setSubject(asunto);
        mensaje.setText(contenido);
        mensaje.setFrom("miguelangel.garcia230@comunidadunir.net"); // mismo que en application.properties

        mailSender.send(mensaje);
    }
}
