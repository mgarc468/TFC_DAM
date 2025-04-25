import React, { useEffect, useState } from "react";
import { FaEdit, FaTrash, FaSave, FaTimes, FaPlus } from "react-icons/fa";

const UserList = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [roles, setRoles] = useState([]);
  const [editandoId, setEditandoId] = useState(null);
  const [formData, setFormData] = useState({ nombre: "", email: "", password: "", rolId: "" });
  const [newUser, setNewUser] = useState({ nombre: "", email: "", password: "", rolId: "" });
  const [filtros, setFiltros] = useState({ nombre: "", email: "", rolId: "" });

  const fetchUsuarios = async () => {
    const res = await fetch("http://localhost:8080/usuario/getAll");
    const data = await res.json();
    setUsuarios(Array.isArray(data) ? data : []);
  };

  const fetchRoles = async () => {
    const res = await fetch("http://localhost:8080/rol/getAll");
    const data = await res.json();
    setRoles(data);
  };

  useEffect(() => {
    fetchUsuarios();
    fetchRoles();
  }, []);

  const handleEdit = (usuario) => {
    setEditandoId(usuario.id);
    setFormData({
      nombre: usuario.nombre,
      email: usuario.email,
      password: usuario.password,
      rolId: usuario.roles?.[0]?.id || "", // rol principal
    });
  };

  const cancelarEdicion = () => {
    setEditandoId(null);
    setFormData({ nombre: "", email: "", password: "", rolId: "" });
  };

  const guardarCambios = async (id) => {
    const res = await fetch(`http://localhost:8080/usuario/update/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        nombre: formData.nombre,
        email: formData.email,
        password: formData.password,
        roles: [
          { id: parseInt(formData.rolId) } // 👈 muy importante que el id sea número
        ]
      }),
    });
  
    if (res.ok) {
      await fetchUsuarios();
      cancelarEdicion();
    } else {
      alert("Error al actualizar usuario");
    }
  };

  const eliminarUsuario = async (id) => {
    if (window.confirm("¿Eliminar este usuario?")) {
      const res = await fetch(`http://localhost:8080/usuario/delete/${id}`, {
        method: "DELETE",
      });
      if (res.ok) fetchUsuarios();
      else alert("Error al eliminar usuario");
    }
  };

  const crearUsuario = async () => {
    if (!newUser.nombre || !newUser.email || !newUser.password || !newUser.rolId) {
      alert("Completa todos los campos");
      return;
    }

    const res = await fetch("http://localhost:8080/usuario/addWithRol", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newUser),
    });

    if (res.ok) {
      await fetchUsuarios();
      setNewUser({ nombre: "", email: "", password: "", rolId: "" });
    } else {
      alert("Error al crear usuario");
    }
  };

  const usuariosFiltrados = usuarios.filter((u) => {
    const rol = u.roles?.[0]?.nombre || "";
    return (
      u.nombre.toLowerCase().includes(filtros.nombre.toLowerCase()) &&
      u.email.toLowerCase().includes(filtros.email.toLowerCase()) &&
      rol.toLowerCase().includes(filtros.rolId.toLowerCase())
    );
  });

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Usuarios Registrados</h2>

      {/* Crear Usuario */}
      <div className="card p-3 mb-4">
        <h5>Añadir Nuevo Usuario</h5>
        <div className="row g-2">
          <div className="col-md-2">
            <input
              type="text"
              className="form-control"
              placeholder="Nombre"
              value={newUser.nombre}
              onChange={(e) => setNewUser({ ...newUser, nombre: e.target.value })}
            />
          </div>
          <div className="col-md-3">
            <input
              type="email"
              className="form-control"
              placeholder="Email"
              value={newUser.email}
              onChange={(e) => setNewUser({ ...newUser, email: e.target.value })}
            />
          </div>
          <div className="col-md-2">
            <input
              type="password"
              className="form-control"
              placeholder="Contraseña"
              value={newUser.password}
              onChange={(e) => setNewUser({ ...newUser, password: e.target.value })}
            />
          </div>
          <div className="col-md-2">
            <select
              className="form-select"
              value={newUser.rolId}
              onChange={(e) => setNewUser({ ...newUser, rolId: e.target.value })}
            >
              <option value="">Rol</option>
              {roles.map((rol) => (
                <option key={rol.id} value={rol.id}>
                  {rol.nombre}
                </option>
              ))}
            </select>
          </div>
          <div className="col-md-3">
            <button className="btn btn-success w-100" onClick={crearUsuario}>
              <FaPlus /> Añadir Usuario
            </button>
          </div>
        </div>
      </div>

      {/* Tabla */}
      <table className="table table-striped table-bordered align-middle">
        <thead className="table-light">
          <tr>
            <th>
              Nombre
              <input
                type="text"
                className="form-control form-control-sm mt-1"
                placeholder="Buscar..."
                value={filtros.nombre}
                onChange={(e) => setFiltros({ ...filtros, nombre: e.target.value })}
              />
            </th>
            <th>
              Email
              <input
                type="text"
                className="form-control form-control-sm mt-1"
                placeholder="Buscar..."
                value={filtros.email}
                onChange={(e) => setFiltros({ ...filtros, email: e.target.value })}
              />
            </th>
            <th>
              Rol
              <input
                type="text"
                className="form-control form-control-sm mt-1"
                placeholder="Buscar..."
                value={filtros.rolId}
                onChange={(e) => setFiltros({ ...filtros, rolId: e.target.value })}
              />
            </th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {usuariosFiltrados.map((usuario) => (
            <tr key={usuario.id}>
              {editandoId === usuario.id ? (
                <>
                  <td>
                    <input
                      type="text"
                      className="form-control"
                      value={formData.nombre}
                      onChange={(e) => setFormData({ ...formData, nombre: e.target.value })}
                    />
                  </td>
                  <td>
                    <input
                      type="email"
                      className="form-control"
                      value={formData.email}
                      onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                    />
                  </td>
                  <td>
                    <select
                      className="form-select"
                      value={formData.rolId}
                      onChange={(e) => setFormData({ ...formData, rolId: e.target.value })}
                    >
                      <option value="">Seleccionar rol</option>
                      {roles.map((rol) => (
                        <option key={rol.id} value={rol.id}>
                          {rol.nombre}
                        </option>
                      ))}
                    </select>
                  </td>
                  <td>
                    <div className="btn-group">
                      <button className="btn btn-success btn-sm" onClick={() => guardarCambios(usuario.id)}>
                        <FaSave />
                      </button>
                      <button className="btn btn-secondary btn-sm" onClick={cancelarEdicion}>
                        <FaTimes />
                      </button>
                    </div>
                  </td>
                </>
              ) : (
                <>
                  <td>{usuario.nombre}</td>
                  <td>{usuario.email}</td>
                  <td>{usuario.roles?.[0]?.nombre || "-"}</td>
                  <td>
                    <div className="btn-group">
                      <button className="btn btn-primary btn-sm" onClick={() => handleEdit(usuario)}>
                        <FaEdit />
                      </button>
                      <button className="btn btn-danger btn-sm" onClick={() => eliminarUsuario(usuario.id)}>
                        <FaTrash />
                      </button>
                    </div>
                  </td>
                </>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserList;
