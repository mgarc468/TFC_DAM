import React, { useState } from 'react';
import ProjectForm from '../componentes/ProyectForm';

const Home = () => {
  const [projects, setProjects] = useState([]);
  const [editingProject, setEditingProject] = useState(null);

  const handleAddProject = (newProject) => {
    setProjects([...projects, newProject]);
  };

  const handleUpdateProject = (updatedProject) => {
    setProjects((prev) =>
      prev.map((proj) =>
        proj.id === updatedProject.id ? updatedProject : proj
      )
    );
    setEditingProject(null);
  };

  const handleEdit = (project) => {
    setEditingProject(project);
  };

  return (
    <div>
      <h1>Gestor de Proyectos</h1>
      <ProjectForm
        onAddProject={handleAddProject}
        onUpdateProject={handleUpdateProject}
        editProject={editingProject}
      />

      <h3>Proyectos creados:</h3>
      <ul>
        {projects.map((project) => (
          <li key={project.id}>
            <strong>{project.nombre}</strong>: {project.descripcion}
            <button onClick={() => handleEdit(project)}>Editar</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Home;
