import React, { useEffect, useState } from "react";
import { FaEdit, FaTrash, FaSave, FaTimes, FaPlus, FaChevronDown, FaChevronUp } from "react-icons/fa";

const formatDateForInput = (fecha) => {
  if (!fecha) return "";
  const d = new Date(fecha);
  const offset = d.getTimezoneOffset();
  d.setMinutes(d.getMinutes() - offset);
  return d.toISOString().slice(0, 10);
};

const ProjectList = () => {
  const [projects, setProjects] = useState([]);
  const [filtered, setFiltered] = useState([]);
  const [editId, setEditId] = useState(null);
  const [formData, setFormData] = useState(initialProjectData());
  const [newProject, setNewProject] = useState(initialProjectData());
  const [filters, setFilters] = useState({});
  const [fasesDisponibles, setFasesDisponibles] = useState([]);
  const [expandedProjectId, setExpandedProjectId] = useState(null);
  const [editingFaseId, setEditingFaseId] = useState(null);
  const [faseForm, setFaseForm] = useState({});
  const [newFase, setNewFase] = useState({ nombre: "", duracion_dias: 1, fecha_inicio: "", fecha_fin: "" });
  const [usuariosDisponibles, setUsuariosDisponibles] = useState([]);
  const [nuevoUsuarioAsignado, setNuevoUsuarioAsignado] = useState({});

  function initialProjectData() {
    return {
      nombre: "",
      descripcion: "",
      presupuesto_estimado: "",
      coste_interno: "",
      coste_externo: "",
      coste_total: "",
      fase_actual: "",
      creado_por: localStorage.getItem("userId") || 1,
    };
  }

  // Cargar usuarios disponibles (al montar el componente)
const fetchUsuarios = async () => {
  const res = await fetch("http://localhost:8080/usuario/getAll");
  const data = await res.json();
  setUsuariosDisponibles(data);
};

// Asignar un usuario a un proyecto
const asignarUsuarioAProyecto = async (proyectoId, usuarioId) => {
  const res = await fetch(`http://localhost:8080/proyecto/asignarUsuario`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ usuarioId, proyectoId }),
  });

  if (res.ok) {
    await fetchProjects(); // recargar proyectos
    setNuevoUsuarioAsignado({}); // limpiar selección
  } else {
    alert("Error al asignar usuario al proyecto");
  }
};

// Eliminar un usuario de un proyecto
const eliminarUsuarioDeProyecto = async (proyectoId, usuarioId) => {
  const res = await fetch(`http://localhost:8080/proyecto/eliminarUsuario`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ usuarioId, proyectoId }),
  });

  if (res.ok) {
    await fetchProjects();
  } else {
    alert("Error al eliminar usuario del proyecto");
  }
};

  const fetchProjects = async () => {
    const res = await fetch("http://localhost:8080/proyecto/dashboard");
    const data = await res.json();
    setProjects(Array.isArray(data) ? data : []);
  };

  const fetchFases = async () => {
    const res = await fetch("http://localhost:8080/fases/nombres");
    const data = await res.json();
    setFasesDisponibles(data);
  };

  useEffect(() => {
    fetchProjects();
    fetchFases();
    fetchUsuarios();
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
    const { creado_por, creadoPor, fases, ...dataToSend } = formData;
    const res = await fetch(`http://localhost:8080/proyecto/update/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(dataToSend),
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
    const userId = localStorage.getItem("userId");

    const projectToSend = {
      ...newProject,
      presupuesto_estimado: parseFloat(newProject.presupuesto_estimado) || 0,
      coste_interno: parseFloat(newProject.coste_interno) || 0,
      coste_externo: parseFloat(newProject.coste_externo) || 0,
      coste_total: parseFloat(newProject.coste_total) || 0,
    };

    const res = await fetch(`http://localhost:8080/proyecto/add?usuarioId=${userId}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(projectToSend),
    });

    if (res.ok) {
      await fetchProjects();
      setNewProject(initialProjectData());
    } else {
      alert("Error al crear proyecto");
    }
  };

  const total = (key) =>
    filtered.reduce((sum, p) => sum + parseFloat(p[key] || 0), 0).toFixed(2);

  const toggleExpand = (id) => {
    setExpandedProjectId(expandedProjectId === id ? null : id);
  };

  const handleEditFase = (fase) => {
    setEditingFaseId(fase.id);
    setFaseForm({ ...fase });
  };

  const cancelEditFase = () => {
    setEditingFaseId(null);
    setFaseForm({});
  };

  const saveFase = async (proyectoId, faseId) => {
    const res = await fetch(`http://localhost:8080/fases/update/${faseId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ ...faseForm, proyecto_id: proyectoId }),
    });

    if (res.ok) {
      await fetchProjects();
      cancelEditFase();
    } else {
      alert("Error al guardar fase");
    }
  };

  const deleteFase = async (faseId) => {
    if (window.confirm("¿Eliminar esta fase?")) {
      console.log("Intentando eliminar fase ID:", faseId); // ← verifica esto
      const res = await fetch(`http://localhost:8080/fases/delete/${faseId}`, {
        method: "DELETE",
      });
      if (res.ok) {
        await fetchProjects();
      } else {
        alert("Error al eliminar fase");
      }
    }
  };

  const addFase = async (proyectoId) => {
    const faseToSend = {
      ...newFase,
      duracion_dias: parseInt(newFase.duracion_dias),
      proyecto_id: proyectoId,
    };

    const res = await fetch(`http://localhost:8080/fases/add`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(faseToSend),
    });

    if (res.ok) {
      await fetchProjects();
      setNewFase({ nombre: "", duracion_dias: 1, fecha_inicio: "", fecha_fin: "" });
    } else {
      alert("Error al añadir fase");
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Gestión de Proyectos</h2>

      {/* Formulario de nuevo proyecto */}
      <div className="card p-3 mb-4">
        <h5>Nuevo Proyecto</h5>
        <div className="row g-2">
          {["nombre", "descripcion", "presupuesto_estimado", "coste_interno", "coste_externo", "coste_total"].map((field) => (
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
            <select
              className="form-select"
              value={newProject.fase_actual}
              onChange={(e) => setNewProject({ ...newProject, fase_actual: e.target.value })}
            >
              <option value="">Selecciona fase</option>
              {fasesDisponibles.map((fase, i) => (
                <option key={i} value={fase}>{fase}</option>
              ))}
            </select>
          </div>
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
            {["nombre", "descripcion", "presupuesto_estimado", "coste_interno", "coste_externo", "coste_total", "fase_actual", "creadoPor"].map((key) => (
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
            <th>Fases</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {filtered.map((project) => (
            <React.Fragment key={project.id}>
              <tr>
                {editId === project.id ? (
                  <>
                    {["nombre", "descripcion", "presupuesto_estimado", "coste_interno", "coste_externo", "coste_total"].map((field) => (
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
                      <select
                        className="form-select"
                        value={formData.fase_actual}
                        onChange={(e) => setFormData({ ...formData, fase_actual: e.target.value })}
                      >
                        <option value="">Selecciona fase</option>
                        {fasesDisponibles.map((fase, i) => (
                          <option key={i} value={fase}>{fase}</option>
                        ))}
                      </select>
                    </td>
                    <td>{project.creadoPor?.nombre || "Sin info"}</td>
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
                    <td className="text-center">
                      <button className="btn btn-sm btn-outline-primary" onClick={() => toggleExpand(project.id)}>
                        {expandedProjectId === project.id ? <FaChevronUp /> : <FaChevronDown />}
                      </button>
        
                    </td>
                    <td>
                      <div className="btn-group">
                        <button className="btn btn-primary btn-sm" onClick={() => handleEdit(project)}><FaEdit /></button>
                        <button className="btn btn-danger btn-sm" onClick={() => deleteProject(project.id)}><FaTrash /></button>
                      </div>
                    </td>
                  </>
                )}
              </tr>

              {/* FASES del proyecto */}
              {expandedProjectId === project.id && (
                <tr>
                  <td colSpan="11">
                    <h6>Fases del Proyecto</h6>
                    <table className="table table-sm table-bordered">
                      <thead className="table-light">
                        <tr>
                          <th>Nombre</th>
                          <th>Duración</th>
                          <th>Inicio</th>
                          <th>Fin</th>
                          <th>Acciones</th>
                        </tr>
                      </thead>
                      <tbody>
                        {project.fases?.map((fase) => (
                          <tr key={fase.id}>
                            {editingFaseId === fase.id ? (
                              <>
                                <td>
                                  <select
                                    className="form-select"
                                    value={faseForm.nombre}
                                    onChange={(e) => setFaseForm({ ...faseForm, nombre: e.target.value })}
                                  >
                                    <option value="">Selecciona fase</option>
                                    {fasesDisponibles.map((fase, i) => (
                                      <option key={i} value={fase}>
                                        {fase}
                                      </option>
                                    ))}
                                  </select>
                                </td>
                                <td><input className="form-control" type="number" value={faseForm.duracion_dias} onChange={(e) => setFaseForm({ ...faseForm, duracion_dias: parseInt(e.target.value) || 0 })} /></td>
                                <td><input
                                  className="form-control"
                                  type="date"
                                  value={formatDateForInput(faseForm.fecha_inicio)}
                                  onChange={(e) => setFaseForm({ ...faseForm, fecha_inicio: e.target.value })}
                                /></td>
                                <td><input
                                  className="form-control"
                                  type="date"
                                  value={formatDateForInput(faseForm.fecha_fin)}
                                  onChange={(e) => setFaseForm({ ...faseForm, fecha_fin: e.target.value })}
                                /></td>
                                <td>
                                  <button className="btn btn-success btn-sm me-1" onClick={() => saveFase(project.id, fase.id)}><FaSave /></button>
                                  <button className="btn btn-secondary btn-sm" onClick={cancelEditFase}><FaTimes /></button>
                                </td>
                              </>
                            ) : (
                              <>
                                <td>{fase.nombre}</td>
                                <td>{fase.duracion_dias}</td>
                                <td>{fase.fecha_inicio?.slice(0, 10)}</td>
                                <td>{fase.fecha_fin?.slice(0, 10)}</td>
                                <td>
                                  <button className="btn btn-sm btn-primary me-1" onClick={() => handleEditFase(fase)}><FaEdit /></button>
                                  <button className="btn btn-sm btn-danger" onClick={() => deleteFase(fase.id)}><FaTrash /></button>
                                </td>
                              </>
                            )}
                          </tr>
                        ))}

                        {/* Añadir nueva fase */}
                        <tr>
                        <td>
                          <select
                            className="form-select"
                            value={newFase.nombre}
                            onChange={(e) =>
                              setNewFase({ ...newFase, nombre: e.target.value })
                            }
                          >
                            <option value="">Selecciona fase</option>
                            {fasesDisponibles.map((fase, i) => (
                              <option key={i} value={fase}>
                                {fase}
                              </option>
                            ))}
                          </select>
                        </td>
                          <td><input className="form-control" type="number" value={newFase.duracion_dias} onChange={(e) => setNewFase({ ...newFase, duracion_dias: e.target.value })} /></td>
                          <td><input className="form-control" type="date" value={newFase.fecha_inicio} onChange={(e) => setNewFase({ ...newFase, fecha_inicio: e.target.value })} /></td>
                          <td><input className="form-control" type="date" value={newFase.fecha_fin} onChange={(e) => setNewFase({ ...newFase, fecha_fin: e.target.value })} /></td>
                          <td>
                            <button className="btn btn-success btn-sm" onClick={() => addFase(project.id)}><FaPlus /></button>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </td>
                </tr>
                
              )}
              {/* USUARIOS asignados al proyecto */}
                <tr>
                  <td colSpan="11">
                    <h6>Usuarios asignados</h6>
                    <table className="table table-sm table-bordered">
                      <thead className="table-light">
                        <tr>
                          <th>Nombre</th>
                          <th>Email</th>
                          <th>Acciones</th>
                        </tr>
                      </thead>
                      <tbody>
                        {project.usuarios && project.usuarios.length > 0 ? (
                          project.usuarios.map((usuario) => (
                            <tr key={usuario.id}>
                              <td>{usuario.nombre}</td>
                              <td>{usuario.email}</td>
                              <td>
                                <button
                                  className="btn btn-sm btn-danger"
                                  onClick={() => eliminarUsuarioDeProyecto(project.id, usuario.id)}
                                >
                                  Quitar
                                </button>
                              </td>
                            </tr>
                          ))
                        ) : (
                          <tr>
                            <td colSpan="3" className="text-center">No hay usuarios asignados</td>
                          </tr>
                        )}

                        {/* Añadir nuevo usuario */}
                        <tr>
                          <td colSpan="3">
                            <div className="d-flex">
                              <select
                                className="form-select me-2"
                                value={nuevoUsuarioAsignado[project.id] || ""}
                                onChange={(e) =>
                                  setNuevoUsuarioAsignado({
                                    ...nuevoUsuarioAsignado,
                                    [project.id]: e.target.value,
                                  })
                                }
                              >
                                <option value="">Selecciona usuario</option>
                                {usuariosDisponibles.map((usuario) => (
                                  <option key={usuario.id} value={usuario.id}>
                                    {usuario.nombre} ({usuario.email})
                                  </option>
                                ))}
                              </select>
                              <button
                                className="btn btn-success"
                                onClick={() =>
                                  asignarUsuarioAProyecto(
                                    project.id,
                                    nuevoUsuarioAsignado[project.id]
                                  )
                                }
                                disabled={!nuevoUsuarioAsignado[project.id]}
                              >
                                Asignar
                              </button>
                            </div>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </td>
                </tr>

            </React.Fragment>
          ))}
        </tbody>
        <tfoot className="table-light">
          <tr>
            <td colSpan={2}><strong>Totales:</strong></td>
            <td>{total("presupuesto_estimado")}</td>
            <td>{total("coste_interno")}</td>
            <td>{total("coste_externo")}</td>
            <td>{total("coste_total")}</td>
            <td colSpan={4}></td>
          </tr>
        </tfoot>
      </table>
    </div>
  );
};

export default ProjectList;
