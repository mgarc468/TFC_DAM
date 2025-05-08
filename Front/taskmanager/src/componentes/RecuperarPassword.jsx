import React, { useState } from "react";

// Componente que permite a los usuarios recuperar su contraseña
const RecuperarPassword = () => {
  // Estado para el correo electrónico ingresado
  const [email, setEmail] = useState("");
  // Estado para el mensaje de respuesta del servidor
  const [mensaje, setMensaje] = useState("");
  // Estado para definir el tipo de alerta (info, success, danger)
  const [tipoMensaje, setTipoMensaje] = useState("info");

  // Manejador del envío del formulario
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevenir recarga de la página

    try {
      // Enviar solicitud POST al backend con el email
      const res = await fetch("http://localhost:8080/usuario/recuperar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email }),
      });

      // Leer la respuesta como texto plano
      const text = await res.text();

      if (res.ok) {
        // Si la respuesta fue exitosa, mostrar mensaje de éxito
        setMensaje(text);
        setTipoMensaje("success");
        setEmail(""); // Limpiar campo
      } else {
        // Si hubo un error, mostrar mensaje de error
        setMensaje(text);
        setTipoMensaje("danger");
      }
    } catch (error) {
      // Error de red o de servidor
      setMensaje("Error al conectar con el servidor.");
      setTipoMensaje("danger");
    }
  };

  return (
    <div className="container mt-5 d-flex justify-content-center">
      {/* Tarjeta con el formulario centrado */}
      <div className="card shadow p-4" style={{ width: "100%", maxWidth: "400px" }}>
        <h4 className="text-center mb-4">¿Olvidaste tu contraseña?</h4>

        {/* Formulario de recuperación */}
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Correo electrónico</label>
            <input
              type="email"
              className="form-control"
              placeholder="ejemplo@correo.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required // Campo obligatorio
            />
          </div>

          {/* Botón de envío */}
          <button type="submit" className="btn btn-primary w-100">
            Enviar nueva contraseña
          </button>
        </form>

        {/* Mostrar mensaje si existe */}
        {mensaje && (
          <div className={`alert alert-${tipoMensaje} mt-3 text-center`} role="alert">
            {mensaje}
          </div>
        )}
      </div>
    </div>
  );
};

export default RecuperarPassword;
