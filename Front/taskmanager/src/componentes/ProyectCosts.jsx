import React, { useEffect, useState } from "react";

const ProjectCosts = () => {
  const [costs, setCosts] = useState([]);
  const [sortBy, setSortBy] = useState("totalCost"); // por defecto, se ordena por coste total

  useEffect(() => {
    fetch("http://localhost:8080/proyecto/costos")  // Endpoint para obtener los costes de los proyectos
      .then((res) => res.json())
      .then((data) => setCosts(data))
      .catch((err) => console.error("Error cargando costes:", err));
  }, []);

  const sortProjects = (projects, criteria) => {
    return projects.sort((a, b) => {
      return b[criteria] - a[criteria];  // Orden descendente por el criterio seleccionado
    });
  };

  const sortedProjects = sortProjects(costs, sortBy);

  return (
    <div>
      <h2>Costes de los Proyectos</h2>

      <div>
        <button onClick={() => setSortBy("totalCost")}>Ordenar por Coste Total</button>
        <button onClick={() => setSortBy("internalCost")}>Ordenar por Coste Interno</button>
        <button onClick={() => setSortBy("externalCost")}>Ordenar por Coste Externo</button>
      </div>

      <ul>
        {sortedProjects.map((project) => (
          <li key={project.id}>
            <strong>{project.nombre}</strong> - {project.descripcion}
            <br />
            <strong>Coste Total: </strong>€{project.totalCost}
            <br />
            <strong>Coste Interno: </strong>€{project.internalCost}
            <br />
            <strong>Coste Externo: </strong>€{project.externalCost}
          </li>
        ))}
      </ul>

      <div>
        <h3>Coste Total Anual: €{costs.reduce((acc, p) => acc + p.totalCost, 0)}</h3>
        <h3>Coste Total Mensual: €{(costs.reduce((acc, p) => acc + p.totalCost, 0) / 12).toFixed(2)}</h3>
      </div>
    </div>
  );
};

export default ProjectCosts;
