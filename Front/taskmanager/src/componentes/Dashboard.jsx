import React, { useEffect, useState } from "react";
import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid
} from "recharts";

const Dashboard = () => {
  const [projects, setProjects] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/proyecto/dashboard")
      .then((res) => res.json())
      .then((data) => {
        console.log("DATA RECIBIDA:", data);
        if (Array.isArray(data)) {
          setProjects(data);
        } else {
          setError("El servidor no devolvió una lista de proyectos.");
        }
      })
      .catch((err) => {
        console.error("Error cargando el dashboard:", err);
        setError("Error al cargar los proyectos.");
      });
  }, []);

  return (
    <div className="container">
      <h2 className="mb-3">Dashboard de Proyectos Activos</h2>
      <p className="text-muted">Visualización de fases por proyecto (estilo Gantt simplificado)</p>

      {error && <div className="alert alert-danger">{error}</div>}

      {Array.isArray(projects) && projects.length > 0 ? (
        projects.map((project) => (
          <div key={project.id} className="mb-5">
            <h4>{project.nombre}</h4>
            <p>{project.descripcion}</p>

            <ResponsiveContainer width="100%" height={150}>
              <BarChart
                data={project.fases.map((fase) => ({
                  name: fase.nombre,
                  Duración: fase.duracionDias,
                }))}
                layout="vertical"
                margin={{ top: 5, right: 30, left: 100, bottom: 5 }}
              >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis type="number" />
                <YAxis type="category" dataKey="name" />
                <Tooltip />
                <Bar dataKey="Duración" fill="#8884d8" />
              </BarChart>
            </ResponsiveContainer>

            <ul>
              {project.fases.map((fase, idx) => (
                <li key={idx}>
                  <strong>{fase.nombre}</strong>: asignado a {fase.asignado}
                </li>
              ))}
            </ul>
          </div>
        ))
      ) : (
        !error && <p className="text-muted">No hay proyectos disponibles.</p>
      )}
    </div>
  );
};

export default Dashboard;
