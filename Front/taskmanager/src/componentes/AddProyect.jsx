import React, { useState } from "react";

const AddProject = () => {
  const [nombre, setNombre] = useState("");
  const [codigo, setCodigo] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [estado, setEstado] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const proyecto = {
      nombre,
      codigo,
      descripcion,
      estado
    };

    try {
      const response = await fetch("http://localhost:8080/proyecto/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(proyecto)
      });

      if (response.ok) {
        alert("Proyecto creado con éxito");
        setNombre("");
        setCodigo("");
        setDescripcion("");
        setEstado("");
      } else {
        alert("Error al crear el proyecto");
      }
    } catch (error) {
      console.error("Error en la solicitud", error);
    }
  };

  return (
    <div>
      <h2>Dar de Alta Proyecto</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Nombre"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Código"
          value={codigo}
          onChange={(e) => setCodigo(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Descripción"
          value={descripcion}
          onChange={(e) => setDescripcion(e.target.value)}
        />
        <input
          type="text"
          placeholder="Estado"
          value={estado}
          onChange={(e) => setEstado(e.target.value)}
        />
        <button type="submit">Crear Proyecto</button>
      </form>
    </div>
  );
};

export default AddProject;
