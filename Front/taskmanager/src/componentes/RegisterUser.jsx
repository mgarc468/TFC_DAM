import React, { useState } from "react";

const RegisterUser = () => {
  const [nombre, setNombre] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const usuario = {
      nombre,
      email,
      password
    };

    try {
      const response = await fetch("http://localhost:8080/usuario/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
      });

      const text = await response.text();

      if (response.ok) {
        alert(text);
        setNombre("");
        setEmail("");
        setPassword("");
      } else {
        alert(text);
      }
    } catch (error) {
      console.error("Error en la solicitud", error);
      alert("Error al registrar el usuario");
    }
  };

  return (
    <div>
      <h2>Registrar Usuario</h2>
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
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Crear Usuario</button>
      </form>
    </div>
  );
};

export default RegisterUser;