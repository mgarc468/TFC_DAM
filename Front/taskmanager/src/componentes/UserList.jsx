import React, { useEffect, useState } from "react";
import { FaEdit, FaTrash, FaSave, FaTimes } from "react-icons/fa";

const UserList = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [editandoId, setEditandoId] = useState(null);
  const [formData, setFormData] = useState({ nombre: "", email: "", password: "" });

  const fetchUsuarios = async () => {
    const res = await fetch("http://localhost:8080/usuario/getAll");
    const data = await res.json();
    setUsuarios(data);
  };

  useEffect(() => {
    fetchUsuarios();
  }, []);

  const handleEdit = (usuario) => {
    setEditandoId(usuario.id);
    setFormData({ nombre: usuario.nombre, email: usuario.email, password: usuario.password });
  };

  const cancelarEdicion = () => {
    setEditandoId(null);
    setFormData({ nombre: "", email: "", password: "" });
  };

  const guardarCambios = async (id) => {
    const res = await fetch(`http://localhost:8080/usuario/update/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
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

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Usuarios Registrados</h2>
      <table className="table table-striped table-bordered align-middle">
        <thead className="table-light">
          <tr>
            <th>Nombre</th>
            <th>Email</th>
            <th>Contraseña</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map((usuario) => (
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
                    <input
                      type="text"
                      className="form-control"
                      value={formData.password}
                      onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                    />
                  </td>
                  <td>
                    <div className="btn-group">
                      <button
                        className="btn btn-success btn-sm"
                        onClick={() => guardarCambios(usuario.id)}
                      >
                        <FaSave />
                      </button>
                      <button
                        className="btn btn-secondary btn-sm"
                        onClick={cancelarEdicion}
                      >
                        <FaTimes />
                      </button>
                    </div>
                  </td>
                </>
              ) : (
                <>
                  <td>{usuario.nombre}</td>
                  <td>{usuario.email}</td>
                  <td>{usuario.password}</td>
                  <td>
                    <div className="btn-group">
                      <button
                        className="btn btn-primary btn-sm"
                        onClick={() => handleEdit(usuario)}
                      >
                        <FaEdit />
                      </button>
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => eliminarUsuario(usuario.id)}
                      >
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
