import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useLocation } from "react-router-dom";
import RutaProtegida from "./componentes/RutaProtegida";

import Login from "./componentes/Login";
import RecuperarPassword from "./componentes/RecuperarPassword";

import NavBar from "./componentes/NavBar";
import Dashboard from "./componentes/Dashboard";
import UserList from "./componentes/UserList";
import ProjectList from "./componentes/ProyectList";

const Layout = () => {
  const location = useLocation();
  const ocultarNavBar = location.pathname === "/" || location.pathname === "/recuperar-password";

  return (
    <>
      {!ocultarNavBar && <NavBar />}
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/recuperar-password" element={<RecuperarPassword />} />

        {/* Rutas protegidas */}
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

const App = () => {
  return (
    <Router>
      <Layout />
    </Router>
  );
};

// Simulando un login (esto puede venir de un contexto o del localStorage)
const loggedUserId = localStorage.getItem("usuario");

export default App;
