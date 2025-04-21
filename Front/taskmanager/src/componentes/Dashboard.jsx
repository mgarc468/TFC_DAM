import React, { useEffect, useState } from "react";
import moment from "moment";
import "moment/locale/es";

const Dashboard = () => {
  const [projects, setProjects] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/proyecto/dashboard")
      .then((res) => res.json())
      .then((data) => {
        if (Array.isArray(data)) {
          setProjects(data);
        } else {
          setError("El servidor no devolvió una lista de proyectos.");
        }
      })
      .catch(() => setError("Error al cargar los proyectos."));
  }, []);

  const today = moment().startOf("day");

  const getMinDate = (fases) =>
    moment.min(fases.map((f) => moment(f.fecha_inicio))).startOf("day");
  const getMaxDate = (fases) =>
    moment.max(fases.map((f) => moment(f.fecha_fin))).startOf("day");

  const getWeekMarkers = (start, end) => {
    const weeks = [];
    let current = start.clone().startOf("isoWeek");
    while (current.isBefore(end)) {
      weeks.push(current.clone());
      current.add(1, "week");
    }
    return weeks;
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-3">Dashboard de Proyectos Activos</h2>
      <p className="text-muted">Visualización temporal por semanas</p>

      {error && <div className="alert alert-danger">{error}</div>}

      {projects.map((project) => {
        if (!project.fases || project.fases.length === 0) return null;

        const minDate = getMinDate(project.fases);
        const maxDate = getMaxDate(project.fases);
        const totalDays = maxDate.diff(minDate, "days") + 1;
        const weekMarkers = getWeekMarkers(minDate, maxDate);

        return (
          <div key={project.id} className="mb-5 border-bottom pb-4">
            <h4>{project.nombre}</h4>
            <p>{project.descripcion}</p>

            {/* Usuarios asignados */}
            {project.usuarios && project.usuarios.length > 0 ? (
              <div className="mb-2">
                <strong>Usuarios asignados:</strong>
                <ul className="mb-0 ps-3">
                  {project.usuarios.map((usuario) => (
                    <li key={usuario.id}>
                      <strong>{usuario.nombre}</strong>{" "}
                      {usuario.roles && usuario.roles.length > 0 && (
                        <span className="text-muted">
                          (
                          {usuario.roles.map((rol, i) => (
                            <span key={rol.id}>
                              {rol.nombre}
                              {i !== usuario.roles.length - 1 ? ", " : ""}
                            </span>
                          ))}
                          )
                        </span>
                      )}
                    </li>
                  ))}
                </ul>
              </div>
            ) : (
              <div className="mb-2 text-muted">Sin usuarios asignados</div>
            )}

            {/* Cabecera de semanas */}
            <div className="position-relative" style={{ marginLeft: 120, height: 50 }}>
              {weekMarkers.map((week, idx) => {
                const daysFromStart = week.diff(minDate, "days");
                const leftPercent = (daysFromStart / totalDays) * 100;

                return (
                  <div
                    key={idx}
                    className="position-absolute text-center"
                    style={{
                      left: `${leftPercent}%`,
                      transform: "translateX(-50%)",
                      fontSize: "0.7rem",
                      color: "#666",
                    }}
                  >
                    Semana {week.isoWeek()}
                    <div className="text-muted" style={{ fontSize: "0.6rem" }}>
                      {week.format("DD/MM")}
                    </div>
                  </div>
                );
              })}

              {/* Línea del día actual */}
              {today.isBetween(minDate, maxDate, undefined, "[]") && (
                <div
                  className="position-absolute bg-danger"
                  style={{
                    left: `${(today.diff(minDate, "days") / totalDays) * 100}%`,
                    top: 0,
                    bottom: 0,
                    width: 2,
                  }}
                />
              )}
            </div>

            {/* Fases del proyecto */}
            {project.fases.map((fase, idx) => {
              const inicio = moment(fase.fecha_inicio).startOf("day");
              const fin = moment(fase.fecha_fin).startOf("day");
              const offset = inicio.diff(minDate, "days");
              const duracion = fin.diff(inicio, "days") + 1;

              return (
                <div className="d-flex align-items-center mt-2" key={idx}>
                  <div style={{ width: 120 }}>{fase.nombre}</div>
                  <div className="position-relative flex-grow-1" style={{ height: 30 }}>
                    <div
                      className="bg-success text-white rounded position-absolute text-center"
                      style={{
                        left: `${(offset / totalDays) * 100}%`,
                        width: `${(duracion / totalDays) * 100}%`,
                        height: "100%",
                        paddingTop: 4,
                        fontSize: "0.75rem",
                      }}
                    >
                      {inicio.format("DD/MM")} - {fin.format("DD/MM")}
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        );
      })}
    </div>
  );
};

export default Dashboard;
