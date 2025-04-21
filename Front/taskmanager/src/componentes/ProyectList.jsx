import React, { useEffect, useState } from "react";
import { FaEdit, FaTrash, FaSave, FaTimes, FaPlus } from "react-icons/fa";

const ProjectList = () => {
  const [projects, setProjects] = useState([]);
  const [filtered, setFiltered] = useState([]);
  const [editId, setEditId] = useState(null);
  const [formData, setFormData] = useState(initialProjectData());
  const [newProject, setNewProject] = useState(initialProjectData());
  const [filters, setFilters] = useState({});

  function initialProjectData() {
    return {
      nombre: "",
      descripcion: "",
      presupuesto_estimado: "",
      coste_interno: 0.0,
      coste_externo: 0.0,
      coste_total: 0.0,
      fase_actual: "",
      creado_por: localStorage.getItem("userId") || 1
    };
  }

  const fetchProjects = async () => {
    const res = await fetch("http://localhost:8080/proyecto/dashboard");
    const data = await res.json();
    setProjects(Array.isArray(data) ? data : []);
  };

  useEffect(() => {
    fetchProjects();
  }, []);

  useEffect(() => {
    applyFilters();
  }, [filters, projects]);

  const applyFilters = () => {
    let temp = [...projects];
    Object.entries(filters).forEach(([key, value]) => {
      if (value) {
        temp = temp.filter((p) =>
          p[key]?.toString().toLowerCase().includes(value.toLowerCase())
        );
      }
    });
    setFiltered(temp);
  };

  const handleFilterChange = (key, value) => {
    setFilters({ ...filters, [key]: value });
  };

  const handleEdit = (project) => {
    setEditId(project.id);
    setFormData({ ...project });
  };

  const cancelEdit = () => {
    setEditId(null);
    setFormData(initialProjectData());
  };

  const saveChanges = async (id) => {
    const res = await fetch(`http://localhost:8080/proyecto/update/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
    });

    if (res.ok) {
      await fetchProjects();
      cancelEdit();
    } else {
      alert("Error al actualizar el proyecto");
    }
  };

  const deleteProject = async (id) => {
    if (window.confirm("¿Eliminar este proyecto?")) {
      const res = await fetch(`http://localhost:8080/proyecto/delete/${id}`, {
        method: "DELETE",
      });
      if (res.ok) fetchProjects();
      else alert("Error al eliminar proyecto");
    }
  };

  const addProject = async () => {
    const res = await fetch("http://localhost:8080/proyecto/add", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newProject),
    });

    if (res.ok) {
      await fetchProjects();
      setNewProject(initialProjectData());
    } else {
      alert("Error al crear proyecto");
    }
  };

  const total = (key) => filtered.reduce((sum, p) => sum + parseFloat(p[key] || 0), 0).toFixed(2);

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Gestión de Proyectos</h2>

      {/* Formulario de nuevo proyecto */}
      <div className="card p-3 mb-4">
        <h5>Nuevo Proyecto</h5>
        <div className="row g-2">
          {["nombre", "descripcion", "fase_actual", "presupuesto_estimado", "coste_interno", "coste_externo", "coste_total"].map((field) => (
            <div className="col-md-3" key={field}>
              <input
                className="form-control"
                type="text"
                placeholder={field.replaceAll("_", " ")}
                value={newProject[field]}
                onChange={(e) => setNewProject({ ...newProject, [field]: e.target.value })}
              />
            </div>
          ))}
          <div className="col-md-3">
            <button className="btn btn-success w-100" onClick={addProject}>
              <FaPlus /> Añadir Proyecto
            </button>
          </div>
        </div>
      </div>

      {/* Tabla de proyectos */}
      <table className="table table-bordered table-striped align-middle">
        <thead className="table-light">
          <tr>
            {["nombre", "descripcion", "presupuesto_estimado", "coste_interno", "coste_externo", "coste_total", "fase_actual", "creado_por"].map((key) => (
              <th key={key}>
                {key.replaceAll("_", " ")}
                <input
                  type="text"
                  className="form-control form-control-sm mt-1"
                  placeholder="Filtrar"
                  onChange={(e) => handleFilterChange(key, e.target.value)}
                />
              </th>
            ))}
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {filtered.map((project) => (
            <tr key={project.id}>
              {editId === project.id ? (
                <>
                  {["nombre", "descripcion", "presupuesto_estimado", "coste_interno", "coste_externo", "coste_total", "fase_actual", "creado_por"].map((field) => (
                    <td key={field}>
                      <input
                        className="form-control"
                        type="text"
                        value={formData[field]}
                        onChange={(e) => setFormData({ ...formData, [field]: e.target.value })}
                      />
                    </td>
                  ))}
                  <td>
                    <div className="btn-group">
                      <button className="btn btn-success btn-sm" onClick={() => saveChanges(project.id)}><FaSave /></button>
                      <button className="btn btn-secondary btn-sm" onClick={cancelEdit}><FaTimes /></button>
                    </div>
                  </td>
                </>
              ) : (
                <>
                  <td>{project.nombre}</td>
                  <td>{project.descripcion}</td>
                  <td>{project.presupuesto_estimado}</td>
                  <td>{project.coste_interno}</td>
                  <td>{project.coste_externo}</td>
                  <td>{project.coste_total}</td>
                  <td>{project.fase_actual}</td>
                  <td>{project.creadoPor?.nombre || "Sin info"}</td>
                  <td>
                    <div className="btn-group">
                      <button className="btn btn-primary btn-sm" onClick={() => handleEdit(project)}><FaEdit /></button>
                      <button className="btn btn-danger btn-sm" onClick={() => deleteProject(project.id)}><FaTrash /></button>
                    </div>
                  </td>
                </>
              )}
            </tr>
          ))}
        </tbody>

        {/* Totales al pie */}
        <tfoot className="table-light">
          <tr>
            <td colSpan={2}><strong>Totales:</strong></td>
            <td>{total("presupuesto_estimado")}</td>
            <td>{total("coste_interno")}</td>
            <td>{total("coste_externo")}</td>
            <td>{total("coste_total")}</td>
            <td colSpan={3}></td>
          </tr>
        </tfoot>
      </table>
    </div>
  );
};

export default ProjectList;
