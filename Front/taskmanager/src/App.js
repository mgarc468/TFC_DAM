import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"; // Ruteo principal
import { useLocation } from "react-router-dom"; // Hook para obtener la ruta actual

// Componentes
import RutaProtegida from "./componentes/RutaProtegida"; // Componente que protege rutas si no hay sesión
import Login from "./componentes/Login";
import RecuperarPassword from "./componentes/RecuperarPassword";
import NavBar from "./componentes/NavBar";
import Dashboard from "./componentes/Dashboard";
import UserList from "./componentes/UserList";
import ProjectList from "./componentes/ProyectList";

// Componente Layout que se encarga de mostrar la NavBar y las rutas
const Layout = () => {
  const location = useLocation(); // Obtenemos la ubicación actual (pathname)

  // Determina si se debe ocultar la NavBar en rutas específicas
  const ocultarNavBar =
    location.pathname === "/" || location.pathname === "/recuperar-password";

  return (
    <>
      {/* NavBar se muestra solo si no estamos en login o recuperar-password */}
      {!ocultarNavBar && <NavBar />}

      {/* Definición de rutas */}
      <Routes>
        {/* Rutas públicas */}
        <Route path="/" element={<Login />} />
        <Route path="/recuperar-password" element={<RecuperarPassword />} />

        {/* Rutas protegidas que requieren sesión activa */}
        <Route
          path="/dashboard"
          element={
            <RutaProtegida>
              <Dashboard />
            </RutaProtegida>
          }
        />
        <Route
          path="/usuarios"
          element={
            <RutaProtegida>
              <UserList />
            </RutaProtegida>
          }
        />
        <Route
          path="/proyectos"
          element={
            <RutaProtegida>
              <ProjectList />
            </RutaProtegida>
          }
        />
      </Routes>
    </>
  );
};

// Componente App principal que inicializa el Router y monta el Layout
const App = () => {
  return (
    <Router>
      <Layout />
    </Router>
  );
};

// Simulación de login (nota: esta línea no afecta el renderizado)
const loggedUserId = localStorage.getItem("usuario");

export default App;
