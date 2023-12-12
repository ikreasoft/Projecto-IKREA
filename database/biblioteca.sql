-- Crear base de datos
DROP DATABASE IF EXISTS BibliotecaGranVia;
CREATE DATABASE BibliotecaGranVia;
-- Borrado de tablas antes de crearlas
DROP TABLE IF EXISTS BibliotecaGranVia.Usuarios;
DROP TABLE IF EXISTS BibliotecaGranVia.UNormal;
DROP TABLE IF EXISTS BibliotecaGranVia.UBibliotecario;
DROP TABLE IF EXISTS BibliotecaGranVia.UAdministrador;
DROP TABLE IF EXISTS BibliotecaGranVia.Ejemplares;
DROP TABLE IF EXISTS BibliotecaGranVia.Titulo;
DROP TABLE IF EXISTS BibliotecaGranVia.Generos;
DROP TABLE IF EXISTS BibliotecaGranVia.Editoriales;
DROP TABLE IF EXISTS BibliotecaGranVia.Estantes;
DROP TABLE IF EXISTS BibliotecaGranVia.Categorias;
DROP TABLE IF EXISTS BibliotecaGranVia.Autor;
DROP TABLE IF EXISTS BibliotecaGranVia.Libros;
DROP TABLE IF EXISTS BibliotecaGranVia.Revistas;
DROP TABLE IF EXISTS BibliotecaGranVia.Prestamo;
DROP TABLE IF EXISTS BibliotecaGranVia.Reserva;
-- Crear la tabla de Usuarios
CREATE TABLE BibliotecaGranVia.Usuarios (
    -- identificador de usuarios del sistema
    id VARCHAR(10) NOT NULL,
    -- datos comunes
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    direccion VARCHAR(50),
    telefono VARCHAR(10),
    -- datos para login, data analytics, publi, etc
    email VARCHAR(100) NOT NULL,
    contrasenia VARCHAR(100) NOT NULL,
    -- control de registros
    fecha_registro DATE,
    -- control de tipos de usuarios
    tipo_usuario VARCHAR(20) NOT NULL,
    PRIMARY KEY(id)
);
-- Usuario normal
CREATE TABLE BibliotecaGranVia.UNormal (
    -- id de usuarios cliente
    id VARCHAR(10)  NOT NULL,
    -- fecha de penalizacion 
    fecha_fin_penalizacion DATE,
    -- claves foraneas necesarias
    FOREIGN KEY (id) REFERENCES Usuarios(id)
);
-- Usuario bibliotecario
CREATE TABLE BibliotecaGranVia.UBibliotecario (
    -- id de usuarios bibliotecario
    id VARCHAR(10)  NOT NULL,
    -- departamento
    departamento VARCHAR(50),
    -- claves foraneas necesarias
    FOREIGN KEY (id) REFERENCES Usuarios(id)
);
-- Usuario administrador
CREATE TABLE BibliotecaGranVia.UAdministrador (
    -- id de usuarios cliente
    id VARCHAR(10)  NOT NULL,
    -- roles y permisos 
    rol VARCHAR(20),
    -- claves foraneas necesarias
    FOREIGN KEY (id) REFERENCES Usuarios(id)
);
-- Crear la tabla de Ejemplares
CREATE TABLE BibliotecaGranVia.Ejemplares (
    id VARCHAR(10) ,
    cantidad_ejemplares INT,
    estado_prestamo VARCHAR(10),
    fecha_adquisicion DATE,
    PRIMARY KEY(id)
);
-- Crear la tabla de Generos
CREATE TABLE BibliotecaGranVia.Generos (
    id VARCHAR(10),
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);
-- Crear la tabla de Editoriales
CREATE TABLE BibliotecaGranVia.Editoriales (
    id VARCHAR(10),
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);
-- Crear la tabla de Estantes
CREATE TABLE BibliotecaGranVia.Estantes (
    id VARCHAR(10),
    numero INT NOT NULL,
    PRIMARY KEY(id)
);
-- Crear la tabla de Categorías
CREATE TABLE BibliotecaGranVia.Categorias (
    id VARCHAR(10),
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);
-- Crear la tabla de Autor
CREATE TABLE BibliotecaGranVia.Autor (
    id VARCHAR(10),
    nombre VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    PRIMARY KEY(id)
);
-- Crear la tabla de Titulos
CREATE TABLE BibliotecaGranVia.Titulo (
    -- identificador del titulo
    id VARCHAR(10),
    -- datos relevantes para los titulo
    genero_id VARCHAR(10) NOT NULL,
    editorial_id VARCHAR(10) NOT NULL,
    estante_id VARCHAR(10) NOT NULL,
    categoria_id VARCHAR(10) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    isbn VARCHAR(10) NOT NULL,
    autor_id VARCHAR(10) NOT NULL,
    -- numero_reserva INT,
    -- claves foraneas necesarias
    -- FOREIGN KEY (id) REFERENCES Ejemplares(id),    
    FOREIGN KEY (genero_id) REFERENCES Generos(id),
    FOREIGN KEY (editorial_id) REFERENCES Editoriales(id),
    FOREIGN KEY (estante_id) REFERENCES Estantes(id),
    FOREIGN KEY (categoria_id) REFERENCES Categorias(id),
    FOREIGN KEY (autor_id) REFERENCES Autor(id),
    PRIMARY KEY(id)
);
-- Crear la tabla de Libros
CREATE TABLE BibliotecaGranVia.Libros (    
    -- datos relevantes para los libros
    anio_publicacion DATE NOT NULL
);
-- Crear la tabla de Revistas
CREATE TABLE BibliotecaGranVia.Revistas (
    -- datos relevantes para las revistas
    fecha_publicacion DATE NOT NULL    
);
-- Crear la tabla de Prestamo
CREATE TABLE BibliotecaGranVia.Prestamo (
    -- id de prestamo
    id VARCHAR(10),
    -- id del libro prestado
    libro_id VARCHAR(10) NOT NULL,
    -- id del usuario que pidió el prestamo
    usuario_id VARCHAR(10) NOT NULL,
    -- fechas de prestamo
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE NOT NULL,
    -- estado del prestamo
    estado BOOLEAN,
    -- comentarios
    comentarios VARCHAR(255),
    -- multa por devolucion del libro fuera de plazo
    multa DECIMAL(10,2), 
    -- claves foraneas necesarias
    FOREIGN KEY (libro_id) REFERENCES Titulo(id),
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id),
    PRIMARY KEY(id)
);
-- Crear la tabla de Reserva
CREATE TABLE BibliotecaGranVia.Reserva (
    -- datos principales para detectar la reserva
    id VARCHAR(10),
    id_usuario VARCHAR(10),
    id_libro VARCHAR(10) NOT NULL,
    -- fecha de reservacion
    fecha_reserva DATE,
    -- claves foraneas necesarias
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id),
    FOREIGN KEY (id_libro) REFERENCES Titulo(id),
    PRIMARY KEY(id)
);
