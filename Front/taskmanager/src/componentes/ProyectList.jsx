import React, { useEffect, useState } from "react";

const ProjectList = () => {
  const [projects, setProjects] = useState([]);
  const [filteredProjects, setFilteredProjects] = useState([]);
  const [filter, setFilter] = useState("activo");

  // Fetch de proyectos
  useEffect(() => {
    fetch("http://localhost:8080/proyecto/list")
      .then((res) => res.json())
      .then((data) => {
        // Si los datos no son un array, asignamos un array vacío
        if (Array.isArray(data)) {
          setProjects(data);
        } else {
          console.warn("Se esperaba un array de proyectos, pero se recibió:", data);
          setProjects([]);  // Asegúrate de asignar un array vacío si no es un array
        }
      })
      .catch((err) => {
        console.error("Error cargando los proyectos:", err);
        setProjects([]);  // En caso de error, también asignamos un array vacío
      });
  }, []);

  // Filtrado de proyectos
  useEffect(() => {
    if (!Array.isArray(projects)) {
      console.error("La variable 'projects' no es un array.");
      return;
    }

    // Filtrar los proyectos por el estado
    const filtered = projects.filter((project) => project.estado === filter);
    setFilteredProjects(filtered);
  }, [projects, filter]);

  // Función para manejar cambios en el filtro
  const handleFilterChange = (event) => {
    setFilter(event.target.value);
  };

  return (
    <div>
      <h2>Lista de Proyectos</h2>
      <div>
        <label>Filtrar por estado:</label>
        <select onChange={handleFilterChange}>
          <option value="activo">Activos</option>
          <option value="inactivo">Inactivos</option>
        </select>
      </div>
      <ul>
        {filteredProjects.length === 0 ? (
          <li>No hay proyectos disponibles</li>
        ) : (
          filteredProjects.map((project) => (
            <li key={project.id}>
              <strong>{project.nombre}</strong> - {project.estado}
            </li>
          ))
        )}
      </ul>
    </div>
  );
};

export default ProjectList;
