import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./componentes/Login";
import RegisterUser from "./componentes/RegisterUser";
import Dashboard from "./componentes/Dashboard";
import AddProject from "./componentes/AddProyect";
import ProjectList from "./componentes/ProyectList";
import ProjectCosts from "./componentes/ProyectCosts";
import RecuperarPassword from "./componentes/RecuperarPassword";
import RutaProtegida from "./componentes/RutaProtegida";
import NavBar from "./componentes/NavBar";

import { useLocation } from "react-router-dom";

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
          path="/register"
          element={
            <RutaProtegida>
              <RegisterUser />
            </RutaProtegida>
          }
        />
        <Route
          path="/dashboard"
          element={
            <RutaProtegida>
              <Dashboard />
            </RutaProtegida>
          }
        />
        <Route
          path="/add-project"
          element={
            <RutaProtegida>
              <AddProject />
            </RutaProtegida>
          }
        />
        <Route
          path="/project-list"
          element={
            <RutaProtegida>
              <ProjectList />
            </RutaProtegida>
          }
        />
        <Route
          path="/project-costs"
          element={
            <RutaProtegida>
              <ProjectCosts />
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

export default App;
