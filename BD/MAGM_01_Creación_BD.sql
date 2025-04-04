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

-- Tabla de Fases de Proyecto
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
('Juan Pérez', 'juan.perez@ejemplo.com', 'password123'),
('Ana Gómez', 'ana.gomez@ejemplo.com', 'password456'),
('Carlos López', 'carlos.lopez@ejemplo.com', 'password789'),
('Luis Martínez', 'luis.martinez@ejemplo.com', 'password321'),
('Marta Fernández', 'marta.fernandez@ejemplo.com', 'password654'),
('Pedro Sánchez', 'pedro.sanchez@ejemplo.com', 'password987'),
('Laura García', 'laura.garcia@ejemplo.com', 'password654'),
('David Torres', 'david.torres@ejemplo.com', 'password159'),
('Sofia Ruiz', 'sofia.ruiz@ejemplo.com', 'password753'),
('Andrés Gómez', 'andres.gomez@ejemplo.com', 'password951');

-- Insertar datos en la tabla 'roles'
INSERT INTO roles (nombre) VALUES
('JEFE_PROYECTO'),
('DESARROLLADOR'),
('QA'),
('ADMINISTRADOR'),
('DISEÑADOR');

-- Asignar roles a los usuarios (tabla intermedia 'usuarios_roles')
-- Juan Pérez tiene el rol de Jefe de Proyecto
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(1, 1); -- Juan Pérez, Jefe de Proyecto

-- Ana Gómez tiene el rol de Desarrollador
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(2, 2); -- Ana Gómez, Desarrollador

-- Carlos López tiene el rol de Jefe de Proyecto y Desarrollador
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(3, 1), -- Carlos López, Jefe de Proyecto
(3, 2); -- Carlos López, Desarrollador

-- Luis Martínez tiene el rol de Desarrollador y QA
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(4, 2), -- Luis Martínez, Desarrollador
(4, 3); -- Luis Martínez, QA

-- Marta Fernández tiene el rol de Administrador
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(5, 4); -- Marta Fernández, Administrador

-- Pedro Sánchez tiene el rol de QA
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(6, 3); -- Pedro Sánchez, QA

-- Laura García tiene el rol de Desarrollador
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(7, 2); -- Laura García, Desarrollador

-- David Torres tiene el rol de Diseñador
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(8, 5); -- David Torres, Diseñador

-- Sofia Ruiz tiene el rol de Desarrollador
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(9, 2); -- Sofia Ruiz, Desarrollador

-- Andrés Gómez tiene el rol de Jefe de Proyecto
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(10, 1); -- Andrés Gómez, Jefe de Proyecto

-- Insertar datos en la tabla 'proyectos'
-- Proyecto 1: "Desarrollo Web"
INSERT INTO proyectos (nombre, descripcion, presupuesto_estimado, fase_actual, creado_por) VALUES
('Desarrollo Web', 'Proyecto de desarrollo de una aplicación web.', 50000.00, 'Requisitos', 1), -- Creado por Juan Pérez
('Aplicación Móvil', 'Desarrollo de una aplicación móvil para Android e iOS.', 70000.00, 'Desarrollo', 2), -- Creado por Ana Gómez
('Sistema de Gestión', 'Sistema de gestión para una empresa de logística.', 60000.00, 'Desarrollo', 3), -- Creado por Carlos López
('Plataforma de E-learning', 'Plataforma online para cursos de formación.', 80000.00, 'Pruebas SIT', 4), -- Creado por Luis Martínez
('App de Noticias', 'Aplicación para lectura de noticias en tiempo real.', 30000.00, 'Requisitos', 5), -- Creado por Marta Fernández
('Gestión de Tareas', 'Sistema para la gestión de tareas dentro de un equipo de trabajo.', 20000.00, 'Desarrollo', 6), -- Creado por Pedro Sánchez
('Rediseño Web', 'Rediseño de la página web de la empresa.', 15000.00, 'Preparación Producción', 7), -- Creado por Laura García
('CRM Empresarial', 'Sistema de gestión de relaciones con clientes.', 95000.00, 'Desarrollo', 8), -- Creado por David Torres
('Automatización de Marketing', 'Plataforma para automatizar procesos de marketing digital.', 120000.00, 'Pruebas UAT', 9), -- Creado por Sofia Ruiz
('Software de Inventario', 'Sistema para la gestión de inventarios de una tienda online.', 40000.00, 'Producción', 10); -- Creado por Andrés Gómez

-- Asignar usuarios a los proyectos (tabla intermedia 'usuarios_proyectos')
-- Proyecto "Desarrollo Web"
INSERT INTO usuarios_proyectos (usuario_id, proyecto_id) VALUES
(1, 1), -- Juan Pérez, Proyecto "Desarrollo Web"
(2, 1), -- Ana Gómez, Proyecto "Desarrollo Web"
(3, 1); -- Carlos López, Proyecto "Desarrollo Web"

-- Proyecto "Aplicación Móvil"
INSERT INTO usuarios_proyectos (usuario_id, proyecto_id) VALUES
(4, 2), -- Luis Martínez, Proyecto "Aplicación Móvil"
(5, 2), -- Marta Fernández, Proyecto "Aplicación Móvil"
(6, 2); -- Pedro Sánchez, Proyecto "Aplicación Móvil"

-- Proyecto "Sistema de Gestión"
INSERT INTO usuarios_proyectos (usuario_id, proyecto_id) VALUES
(3, 3), -- Carlos López, Proyecto "Sistema de Gestión"
(7, 3), -- Laura García, Proyecto "Sistema de Gestión"
(8, 3); -- David Torres, Proyecto "Sistema de Gestión"

-- Proyecto "Plataforma de E-learning"
INSERT INTO usuarios_proyectos (usuario_id, proyecto_id) VALUES
(4, 4), -- Luis Martínez, Proyecto "Plataforma de E-learning"
(6, 4), -- Pedro Sánchez, Proyecto "Plataforma de E-learning"
(10, 4); -- Andrés Gómez, Proyecto "Plataforma de E-learning"

-- Proyecto "App de Noticias"
INSERT INTO usuarios_proyectos (usuario_id, proyecto_id) VALUES
(5, 5), -- Marta Fernández, Proyecto "App de Noticias"
(7, 5), -- Laura García, Proyecto "App de Noticias"
(9, 5); -- Sofia Ruiz, Proyecto "App de Noticias"

-- Insertar fases de proyectos
-- Fases para el proyecto "Desarrollo Web"
INSERT INTO fases_proyecto (proyecto_id, nombre, duracion_dias, fecha_inicio, fecha_fin) VALUES
(1, 'Requisitos', 10, '2025-04-01', '2025-04-10'),
(1, 'Desarrollo', 20, '2025-04-11', '2025-04-30');

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
