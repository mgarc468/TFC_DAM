import React from "react";
import { Link, useNavigate } from "react-router-dom"; // Link para navegación entre rutas, useNavigate para redirección programada
import "bootstrap/dist/css/bootstrap.min.css"; // Estilos de Bootstrap

// Componente de barra de navegación
const NavBar = () => {
  const navigate = useNavigate(); // Hook para redireccionar

  // Función que se ejecuta al hacer clic en "Cerrar Sesión"
  const handleLogout = () => {
    localStorage.removeItem("usuario"); // Elimina la información del usuario del almacenamiento local
    navigate("/"); // Redirige al inicio de sesión o página principal
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
      <div className="container-fluid">
        {/* Logo o nombre de la aplicación, redirige al inicio */}
        <Link className="navbar-brand" to="/">TaskManager</Link>

        {/* Botón hamburguesa para vista móvil */}
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        {/* Contenedor colapsable con los enlaces de navegación */}
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            {/* Enlace al dashboard */}
            <li className="nav-item">
              <Link className="nav-link" to="/dashboard">Dashboard</Link>
            </li>

            {/* Enlace a la gestión de usuarios */}
            <li className="nav-item">
              <Link className="nav-link" to="/usuarios">Gestión de Usuarios</Link>
            </li>

            {/* Enlace a la gestión de proyectos */}
            <li className="nav-item">
              <Link className="nav-link" to="/proyectos">Gestión de Proyectos</Link>
            </li>

            {/* Botón para cerrar sesión */}
            <li className="nav-item">
              <button className="btn btn-outline-light ms-3" onClick={handleLogout}>
                Cerrar Sesión
              </button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
