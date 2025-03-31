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
    nombre ENUM('JEFE_PROYECTO', 'DESARROLLADOR') NOT NULL
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
    descripcion TEXT,
    presupuesto_estimado DECIMAL(10,2),
    coste_interno DECIMAL(10,2) DEFAULT 0.00,
    coste_externo DECIMAL(10,2) DEFAULT 0.00,
    coste_total DECIMAL(10,2) GENERATED ALWAYS AS (coste_interno + coste_externo) STORED,
    fase_actual ENUM('Requisitos', 'Desarrollo', 'Pruebas SIT', 'Pruebas UAT', 'Preparación Producción', 'Producción') DEFAULT 'Requisitos',
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
    nombre ENUM('Requisitos', 'Desarrollo', 'Pruebas SIT', 'Pruebas UAT', 'Preparación Producción', 'Producción') NOT NULL,
    duracion_dias INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    FOREIGN KEY (proyecto_id) REFERENCES proyectos(id) ON DELETE CASCADE
);