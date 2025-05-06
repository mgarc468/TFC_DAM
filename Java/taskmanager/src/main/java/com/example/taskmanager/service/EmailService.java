package com.example.taskmanager.service;

/**
 * Servicio para el envío de correos electrónicos.
 */
public interface EmailService {

    /**
     * Envía un correo electrónico a una dirección específica.
     *
     * @param destino   dirección de correo del destinatario
     * @param asunto    asunto del mensaje
     * @param contenido cuerpo del mensaje (puede ser texto plano o HTML)
     */
    void enviarCorreo(String destino, String asunto, String contenido);
}
