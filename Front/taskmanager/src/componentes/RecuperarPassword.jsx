import React, { useState } from "react";

const RecuperarPassword = () => {
  const [email, setEmail] = useState("");
  const [mensaje, setMensaje] = useState("");
  const [tipoMensaje, setTipoMensaje] = useState("info");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch("http://localhost:8080/usuario/recuperar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email }),
      });

      const text = await res.text();

      if (res.ok) {
        setMensaje(text);
        setTipoMensaje("success");
        setEmail("");
      } else {
        setMensaje(text);
        setTipoMensaje("danger");
      }
    } catch (error) {
      setMensaje("Error al conectar con el servidor.");
      setTipoMensaje("danger");
    }
  };

  return (
    <div className="container mt-5 d-flex justify-content-center">
      <div className="card shadow p-4" style={{ width: "100%", maxWidth: "400px" }}>
        <h4 className="text-center mb-4">¿Olvidaste tu contraseña?</h4>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Correo electrónico</label>
            <input
              type="email"
              className="form-control"
              placeholder="ejemplo@correo.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn btn-primary w-100">
            Enviar nueva contraseña
          </button>
        </form>

        {mensaje && (
          <div className={`alert alert-${tipoMensaje} mt-3 text-center`} role="alert">
            {mensaje}
          </div>
        )}
      </div>
    </div>
  );
};

export default RecuperarPassword;
