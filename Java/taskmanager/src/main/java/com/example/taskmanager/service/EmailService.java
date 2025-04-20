package com.example.taskmanager.service;

public interface EmailService {
    void enviarCorreo(String destino, String asunto, String contenido);
}
