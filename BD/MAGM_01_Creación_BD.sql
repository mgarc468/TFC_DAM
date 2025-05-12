CREATE DATABASE taskmanager;
USE taskmanager;

-- Tabla de Usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Roles
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla intermedia usuarios-roles 
CREATE TABLE usuarios_roles (
    usuario_id INT,
    rol_id INT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Tabla de Proyectos
CREATE TABLE proyectos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300),
    presupuesto_estimado DECIMAL(10,2),
    coste_interno DECIMAL(10,2) DEFAULT 0.00,
    coste_externo DECIMAL(10,2) DEFAULT 0.00,
    coste_total DECIMAL(10,2) DEFAULT 0.00,
    fase_actual VARCHAR(100),
    creado_por INT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (creado_por) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- Tabla intermedia usuarios-proyectos
CREATE TABLE usuarios_proyectos (
    usuario_id INT,
    proyecto_id INT,
    PRIMARY KEY (usuario_id, proyecto_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (proyecto_id) REFERENCES proyectos(id) ON DELETE CASCADE
);

-- Tabla de fases-proyecto
CREATE TABLE fases_proyecto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proyecto_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    duracion_dias INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    FOREIGN KEY (proyecto_id) REFERENCES proyectos(id) ON DELETE CASCADE
);


-- Datos de prueba

-- Insertar datos en la tabla 'usuarios'
INSERT INTO usuarios (nombre, email, password) VALUES
('Miguel Ángel García', 'miguel.garcia@ejemplo.com', 'password123'),
('Pablo Fuente', 'pablo.fuente@ejemplo.com', 'password123'),
('Cristina Casalé', 'cristina.casale@ejemplo.com', 'password789'),
('Jesús Manuel Lozano', 'jesus.lozano@ejemplo.com', 'password321'),
('Cristina Gil', 'cristina.gil@ejemplo.com', 'password654'),
('Administrador', 'administrador@ejemplo.com', 'admin'),
('Juan Vaquerizo', 'juan.vaquerizo@ejemplo.com', 'password654'),
('Enrique Artagoitia', 'enrique.artagoitia@ejemplo.com', 'password159'),
('Lidia Flores', 'lidia.flores@ejemplo.com', 'password753'),
('Alfonso Dominguez', 'alfonso.dominguez@ejemplo.com', 'password753'),
('Fernando Gil', 'fernando.gil@ejemplo.com', 'password753'),
('Josefina Santos', 'josefina.santos@ejemplo.com', 'password951');

-- Insertar datos en la tabla 'roles'
INSERT INTO roles (nombre) VALUES
('JEFE_PROYECTO'),
('DESARROLLADOR'),
('QA'),
('ADMINISTRADOR'),
('DISEÑADOR');

-- Asignar roles a los usuarios (tabla intermedia 'usuarios_roles')

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(1, 1); 

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(2, 2); 

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(3, 1);

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(4, 2); 

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(5, 4); 

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(6, 3); 

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(7, 2); 

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(8, 5); 

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(9, 2); 

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(10, 1); 

-- Insertar datos en la tabla 'proyectos'

INSERT INTO proyectos (nombre, descripcion, presupuesto_estimado, fase_actual, creado_por) VALUES
('Desarrollo Web', 'Proyecto de desarrollo de una aplicación web.', 50000.00, 'Requisitos', 1), 
('Aplicación Móvil', 'Desarrollo de una aplicación móvil para Android e iOS.', 70000.00, 'Desarrollo', 2), 
('Sistema de Gestión', 'Sistema de gestión para una empresa de logística.', 60000.00, 'Desarrollo', 3), 
('Plataforma de E-learning', 'Plataforma online para cursos de formación.', 80000.00, 'Pruebas SIT', 4), 
('App de Noticias', 'Aplicación para lectura de noticias en tiempo real.', 30000.00, 'Requisitos', 5), 
('Gestión de Tareas', 'Sistema para la gestión de tareas dentro de un equipo de trabajo.', 20000.00, 'Desarrollo', 6), 
('Rediseño Web', 'Rediseño de la página web de la empresa.', 15000.00, 'Preparación Producción', 7), 
('CRM Empresarial', 'Sistema de gestión de relaciones con clientes.', 95000.00, 'Desarrollo', 8), 
('Automatización de Marketing', 'Plataforma para automatizar procesos de marketing digital.', 120000.00, 'Pruebas UAT', 9), 
('Software de Inventario', 'Sistema para la gestión de inventarios de una tienda online.', 40000.00, 'Producción', 10); 

-- Asignar usuarios a los proyectos (tabla intermedia 'usuarios_proyectos')
-- Proyecto "Desarrollo Web"
INSERT INTO usuarios_proyectos (usuario_id, proyecto_id) VALUES
(1, 1), 
(2, 1), 
(3, 1); 

-- Proyecto "Aplicación Móvil"
INSERT INTO usuarios_proyectos (usuario_id, proyecto_id) VALUES
(4, 2), 
(5, 2), 
(6, 2); 

-- Insertar fases de proyectos
-- Fases para el proyecto "Desarrollo Web"
INSERT INTO fases_proyecto (proyecto_id, nombre, duracion_dias, fecha_inicio, fecha_fin) VALUES
(1, 'Requisitos', 7, '2025-03-31', '2025-04-06'),
(1, 'Viabilidad', 7, '2025-04-07', '2025-04-13'),
(1, 'DSC Comité', 7, '2025-04-14', '2025-04-20'),
(1, 'Desarrollo + Pruebas SIT', 14, '2025-04-21', '2025-04-27'),
(1, 'Pruebas UAT', 7, '2025-04-28', '2025-05-04'),
(1, 'PPP', 1, '2025-05-05', '2025-05-05'),
(1, 'Subida a Producción', 1, '2025-05-06', '2025-05-06'),
(1, 'Entregado', 999, '2025-05-07', '2100-01-01');

-- Fases para el proyecto "Aplicación Móvil"
INSERT INTO fases_proyecto (proyecto_id, nombre, duracion_dias, fecha_inicio, fecha_fin) VALUES
(2, 'Desarrollo', 15, '2025-04-01', '2025-04-15'),
(2, 'Pruebas SIT', 10, '2025-04-16', '2025-04-25');

-- Fases para el proyecto "Sistema de Gestión"
INSERT INTO fases_proyecto (proyecto_id, nombre, duracion_dias, fecha_inicio, fecha_fin) VALUES
(3, 'Requisitos', 12, '2025-04-05', '2025-04-16'),
(3, 'Desarrollo', 25, '2025-04-17', '2025-05-11');

-- Fases para el proyecto "Plataforma de E-learning"
INSERT INTO fases_proyecto (proyecto_id, nombre, duracion_dias, fecha_inicio, fecha_fin) VALUES
(4, 'Pruebas SIT', 8, '2025-04-10', '2025-04-18'),
(4, 'Pruebas UAT', 12, '2025-04-19', '2025-05-01');


-- Activar el event scheduler (si no está activo)
SET GLOBAL event_scheduler = ON;

-- Crear el EVENTO programado para actualizar automáticamente las fases
DROP EVENT IF EXISTS actualizar_fase_actual;

CREATE EVENT actualizar_fase_actual
ON SCHEDULE EVERY 5 MINUTE
DO
  UPDATE proyectos p
  LEFT JOIN (
      SELECT 
          f.proyecto_id,
          f.nombre
      FROM fases_proyecto f
      WHERE CURDATE() BETWEEN f.fecha_inicio AND f.fecha_fin
      ORDER BY f.fecha_inicio ASC
      LIMIT 1
  ) f ON p.id = f.proyecto_id
  SET p.fase_actual = 
    CASE
      -- Si tiene una fase llamada 'Entregado', ese es el estado
      WHEN EXISTS (
        SELECT 1 
        FROM fases_proyecto fp 
        WHERE fp.proyecto_id = p.id AND fp.nombre = 'Entregado'
      ) THEN 'Entregado'

      -- Si hoy no hay ninguna fase activa, pero hay fases anteriores o futuras → Entre Fases
      WHEN NOT EXISTS (
        SELECT 1 
        FROM fases_proyecto fa 
        WHERE fa.proyecto_id = p.id 
          AND CURDATE() BETWEEN fa.fecha_inicio AND fa.fecha_fin
      )
      AND EXISTS (
        SELECT 1 
        FROM fases_proyecto fb 
        WHERE fb.proyecto_id = p.id
      ) THEN 'Entre Fases'

      -- Si hay una fase activa → nombre de esa fase
      ELSE COALESCE(f.nombre, 'Sin fase')
    END;
