import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Hook para redirección programada

// Componente de inicio de sesión
const Login = () => {
  // Estados para almacenar los valores del formulario
  const [usuario, setUsuario] = useState("");   // Email del usuario
  const [password, setPassword] = useState(""); // Contraseña

  const navigate = useNavigate(); // Hook para redireccionar tras login exitoso

  // Manejador del envío del formulario
  const handleLogin = async (e) => {
    e.preventDefault(); // Prevenir recarga de la página

    try {
      // Petición al backend para autenticar al usuario
      const response = await fetch("http://localhost:8080/usuario/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email: usuario, password }), // Enviamos email y contraseña
      });

      if (response.ok) {
        const data = await response.json();

        // Guardar información del usuario en localStorage
        localStorage.setItem("userId", data.id);         // ID del usuario
        localStorage.setItem("usuario", data.email);     // Email (opcional)
        localStorage.setItem("nombre", data.nombre);     // Nombre (opcional)

        // Redirigir al dashboard tras login exitoso
        navigate("/dashboard");
      } else {
        alert("Login incorrecto"); // Credenciales inválidas
      }
    } catch (error) {
      console.error("Error en login:", error);
      alert("Error en login"); // Error de red o servidor
    }
  };

  return (
    <div className="container mt-5 d-flex justify-content-center">
      <div className="card p-4 shadow" style={{ width: "100%", maxWidth: "400px" }}>
        <h3 className="text-center mb-3">Iniciar Sesión</h3>

        {/* Formulario de login */}
        <form onSubmit={handleLogin}>
          <div className="mb-3">
            <label className="form-label">Correo electrónico</label>
            <input
              type="email"
              className="form-control"
              placeholder="ejemplo@correo.com"
              value={usuario}
              onChange={(e) => setUsuario(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Contraseña</label>
            <input
              type="password"
              className="form-control"
              placeholder="••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          {/* Botón para enviar el formulario */}
          <button type="submit" className="btn btn-primary w-100">
            Entrar
          </button>

          {/* Enlace a recuperación de contraseña */}
          <div className="text-center mt-3">
            <a href="/recuperar-password">¿Olvidaste tu contraseña?</a>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
