import React, { useEffect, useState } from "react";
import { FaEdit, FaTrash, FaSave, FaTimes, FaPlus } from "react-icons/fa";

// Componente principal que gestiona la lista de usuarios
const UserList = () => {
  // Estados para almacenar datos de usuarios, roles, formularios y filtros
  const [usuarios, setUsuarios] = useState([]);
  const [roles, setRoles] = useState([]);
  const [editandoId, setEditandoId] = useState(null);
  const [formData, setFormData] = useState({ nombre: "", email: "", password: "", rolId: "" });
  const [newUser, setNewUser] = useState({ nombre: "", email: "", password: "", rolId: "" });
  const [filtros, setFiltros] = useState({ nombre: "", email: "", rolId: "" });

  // Obtener la lista de usuarios desde el backend
  const fetchUsuarios = async () => {
    const res = await fetch("http://localhost:8080/usuario/getAll");
    const data = await res.json();
    setUsuarios(Array.isArray(data) ? data : []);
  };

  // Obtener la lista de roles desde el backend
  const fetchRoles = async () => {
    const res = await fetch("http://localhost:8080/rol/getAll");
    const data = await res.json();
    setRoles(data);
  };

  // Cargar usuarios y roles al montar el componente
  useEffect(() => {
    fetchUsuarios();
    fetchRoles();
  }, []);

  // Activar modo edición para un usuario específico
  const handleEdit = (usuario) => {
    setEditandoId(usuario.id);
    setFormData({
      nombre: usuario.nombre,
      email: usuario.email,
      password: usuario.password,
      rolId: usuario.roles?.[0]?.id || "", // rol principal
    });
  };

  // Cancelar la edición y limpiar el formulario
  const cancelarEdicion = () => {
    setEditandoId(null);
    setFormData({ nombre: "", email: "", password: "", rolId: "" });
  };

  // Guardar los cambios realizados a un usuario
  const guardarCambios = async (id) => {
    const res = await fetch(`http://localhost:8080/usuario/update/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        nombre: formData.nombre,
        email: formData.email,
        password: formData.password,
        roles: [
          { id: parseInt(formData.rolId) } // importante que el ID sea numérico
        ]
      }),
    });

    // Si la respuesta es exitosa, se actualiza la lista y se sale del modo edición
    if (res.ok) {
      await fetchUsuarios();
      cancelarEdicion();
    } else {
      alert("Error al actualizar usuario");
    }
  };

  // Eliminar un usuario tras confirmación del usuario
  const eliminarUsuario = async (id) => {
    if (window.confirm("¿Eliminar este usuario?")) {
      const res = await fetch(`http://localhost:8080/usuario/delete/${id}`, {
        method: "DELETE",
      });
      if (res.ok) fetchUsuarios();
      else alert("Error al eliminar usuario");
    }
  };

  // Crear un nuevo usuario con los datos del formulario
  const crearUsuario = async () => {
    // Validar que todos los campos estén llenos
    if (!newUser.nombre || !newUser.email || !newUser.password || !newUser.rolId) {
      alert("Completa todos los campos");
      return;
    }

    const res = await fetch("http://localhost:8080/usuario/addWithRol", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newUser),
    });

    // Si es exitoso, se recarga la lista y se limpia el formulario
    if (res.ok) {
      await fetchUsuarios();
      setNewUser({ nombre: "", email: "", password: "", rolId: "" });
    } else {
      alert("Error al crear usuario");
    }
  };

  // Filtrado de usuarios basado en el nombre, email y rol
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

      {/* Sección para añadir nuevo usuario */}
      <div className="card p-3 mb-4">
        <h5>Añadir Nuevo Usuario</h5>
        <div className="row g-2">
          {/* Campo: Nombre */}
          <div className="col-md-2">
            <input
              type="text"
              className="form-control"
              placeholder="Nombre"
              value={newUser.nombre}
              onChange={(e) => setNewUser({ ...newUser, nombre: e.target.value })}
            />
          </div>
          {/* Campo: Email */}
          <div className="col-md-3">
            <input
              type="email"
              className="form-control"
              placeholder="Email"
              value={newUser.email}
              onChange={(e) => setNewUser({ ...newUser, email: e.target.value })}
            />
          </div>
          {/* Campo: Contraseña */}
          <div className="col-md-2">
            <input
              type="password"
              className="form-control"
              placeholder="Contraseña"
              value={newUser.password}
              onChange={(e) => setNewUser({ ...newUser, password: e.target.value })}
            />
          </div>
          {/* Campo: Rol */}
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
          {/* Botón: Añadir */}
          <div className="col-md-3">
            <button className="btn btn-success w-100" onClick={crearUsuario}>
              <FaPlus /> Añadir Usuario
            </button>
          </div>
        </div>
      </div>

      {/* Tabla de usuarios */}
      <table className="table table-striped table-bordered align-middle">
        <thead className="table-light">
          <tr>
            {/* Filtros en cabecera */}
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
                // Modo edición de fila
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
                // Modo vista de fila
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
