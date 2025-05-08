import React from "react";
import { Navigate } from "react-router-dom";

// Componente de ruta protegida que verifica si hay un usuario autenticado
const RutaProtegida = ({ children }) => {
  // Obtiene la información del usuario almacenada en localStorage
  const usuario = localStorage.getItem("usuario");

  // Si no hay un usuario autenticado, redirige al inicio de sesión (ruta "/")
  if (!usuario) {
    return <Navigate to="/" />;
  }

  // Si el usuario está autenticado, permite acceder al contenido protegido
  return children;
};

export default RutaProtegida;
