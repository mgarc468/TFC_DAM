import React from "react";
import { Navigate } from "react-router-dom";

const RutaProtegida = ({ children }) => {
  const usuario = localStorage.getItem("usuario");

  if (!usuario) {
    return <Navigate to="/" />;
  }

  return children;
};

export default RutaProtegida;