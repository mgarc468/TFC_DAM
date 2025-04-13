import React from "react";
import { Link } from "react-router-dom";

const NavBar = () => {
  return (
    <div style={{ marginBottom: "2rem" }}>
      <nav>
        <ul>
          <li>
            <Link to="/">Login</Link>
          </li>
          <li>
            <Link to="/register">Registrar Usuario</Link>
          </li>
          <li>
            <Link to="/add-project">Agregar Proyecto</Link>
          </li>
          <li>
            <Link to="/project-list">Listado de Proyectos</Link>
          </li>
          <li>
            <Link to="/dashboard">Dashboard</Link>
          </li>
          <li>
            <Link to="/project-costs">Costes de Proyectos</Link>
          </li>
        </ul>
      </nav>
    </div>
  );
};

export default NavBar;
