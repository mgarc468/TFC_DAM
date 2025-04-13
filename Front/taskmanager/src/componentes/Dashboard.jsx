import React, { useEffect, useState } from "react";
import { ResponsiveContainer, BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid } from "recharts";

const Dashboard = () => {
  const [projects, setProjects] = useState([]);

  useEffect(() => {
    // Simulamos datos para cada proyecto con fases y asignaciones
    fetch("http://localhost:8080/proyecto/dashboard")
      .then((res) => res.json())
      .then((data) => setProjects(data))
      .catch((err) => console.error("Error cargando el dashboard:", err));
  }, []);

  return (
    <div>
      <h2>Dashboard de Proyectos Activos</h2>
      <p>Visualización de fases por proyecto (estilo Gantt simplificado)</p>
      {projects.map((project) => (
        <div key={project.id} style={{ marginBottom: "2rem" }}>
          <h3>{project.nombre}</h3>
          <p>{project.descripcion}</p>
          <ResponsiveContainer width="100%" height={150}>
            <BarChart
              data={project.fases.map((fase, index) => ({
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
              <li key={idx}><strong>{fase.nombre}</strong>: asignado a {fase.asignado}</li>
            ))}
          </ul>
        </div>
      ))}
    </div>
  );
};

export default Dashboard;