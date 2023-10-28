-- Crear datos de prueba para la tabla Usuarios
INSERT INTO BibliotecaGranVia.Usuarios (id, nombre, apellidos, fecha_nacimiento, direccion, telefono, email, contrasenia, fecha_registro, tipo_usuario) VALUES
(1, 'Juan', 'Pérez', '1990-01-01', 'Calle Mayor, 10', '912345678', 'juan.perez@bibliotecagranvia.com', 'pass123', '2023-07-20', 'Usuario normal'),
(2, 'María', 'López', '1991-02-02', 'Calle Gran Vía, 20', '913456789', 'maria.lopez@bibliotecagranvia.com', 'pass456', '2023-07-21', 'Usuario normal'),
(3, 'Antonio', 'García', '1992-03-03', 'Calle Alcalá, 30', '914567890', 'antonio.garcia@bibliotecagranvia.com', 'pass789', '2023-07-22', 'Usuario bibliotecario'),
(4, 'Pedro', 'Martínez', '1993-04-04', 'Calle Princesa, 40', '915678901', 'pedro.martinez@bibliotecagranvia.com', 'pass901', '2023-07-23', 'Usuario administrador');

-- Crear datos de prueba para la tabla UNormal
INSERT INTO BibliotecaGranVia.UNormal (id, fecha_fin_penalizacion) VALUES
(1, '2023-08-01'),
(2, '2023-08-02'),
(3, NULL);

-- Crear datos de prueba para la tabla UBibliotecario
INSERT INTO BibliotecaGranVia.UBibliotecario (id, departamento) VALUES
(3, 'Departamento de literatura'),
(4, 'Departamento de ciencias');

-- Crear datos de prueba para la tabla UAdministrador
INSERT INTO BibliotecaGranVia.UAdministrador (id, rol) VALUES
(1, 'Administrador principal'),
(2, 'Administrador de usuarios'),
(3, 'Administrador de sistemas');

-- Crear datos de prueba para la tabla Ejemplares
INSERT INTO BibliotecaGranVia.Ejemplares (id, cantidad_ejemplares, estado_prestamo, fecha_adquisicion) VALUES
(1, 10, 'Disponible', '2023-07-20'),
(2, 5, 'Prestado', '2023-07-21'),
(3, 2, 'Reservado', '2023-07-22'),
(4, 1, 'Dañado', '2023-07-23');

-- Crear datos de prueba para la tabla Generos
INSERT INTO BibliotecaGranVia.Generos (id, nombre) VALUES
(1, 'Ficción'),
(2, 'No ficción'),
(3, 'Ciencia ficción'),
(4, 'Fantasía');

-- Crear datos de prueba para la tabla Editoriales
INSERT INTO BibliotecaGranVia.Editoriales (id, nombre, direccion) VALUES
(1, 'Editorial Planeta', 'Avenida Diagonal, 662, Barcelona'),
(2, 'Editorial Penguin Random House', 'Calle Serrano, 61, Madrid'),
(3, 'Editorial Anagrama', 'Calle Mallorca, 288, Barcelona'),
(4, 'Editorial Seix Barral', 'Calle Boteros, 13, Barcelona');

-- Crear datos de prueba para la tabla Estantes
INSERT INTO BibliotecaGranVia.Estantes (id, numero) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

-- Crear datos de prueba para la tabla Categorías
INSERT INTO BibliotecaGranVia.Categorias (id, nombre) VALUES
(1, 'Novela'),
(2, 'Poesía'),
(3, 'Ensayo'),
(4, 'Biografía'),
(5, 'Revista Semanal'),
(6, 'Revista Profesional'),
(7, 'Revista Política'),
(8, 'Cultura');

-- Crear datos de prueba para la tabla Autor
INSERT INTO BibliotecaGranVia.Autor (id, nombre, apellidos, fecha_nacimiento) VALUES
(1, 'J.R.R. Tolkien', 'John Ronald Reuel Tolkien', '1892-01-03'),
(2, 'Gabriel García Márquez', 'Gabriel José de la Concordia García Márquez', '1927-03-06'),
(3, 'Haruki Murakami', 'Haruki Murakami', '1949-01-12'),
(4, 'Stephen King', 'Stephen Edwin King', '1947-09-21');

-- Crear datos de prueba para la tabla Titulos
INSERT INTO BibliotecaGranVia.Titulo (id, genero_id, editorial_id, estante_id, categoria_id, titulo, isbn, autor_id) VALUES
(1, 1, 1, 1, 1, 'El Señor de los Anillos', '978-84-350-0975-2', 1),
(2, 2, 2, 2, 2, 'Cien años de soledad', '978-84-306-0039-8', 2),
(3, 3, 3, 3, 3, '1Q84', '978-84-233-5249-0', 3),
(4, 4, 4, 4, 4, 'It', '978-84-350-0975-2', 4),
(5, 4, 4, 4, 5, 'Acerca De Ti', '350-0975-2', 1);
-- Crear datos de prueba para la tabla Libros
INSERT INTO BibliotecaGranVia.Libros VALUES
(1954),
(1967),
(2009),
(1986);
-- Crear datos de prueba para la tabla Revistas
INSERT INTO BibliotecaGranVia.Revistas VALUES
(2023);
-- Crear datos de prueba para la tabla Prestamo
INSERT INTO BibliotecaGranVia.Prestamo (id, libro_id, usuario_id, fecha_prestamo, fecha_devolucion, estado, comentarios, multa) VALUES
(1, 1, 1, '2023-07-20', '2023-08-03', TRUE, NULL, NULL),
(2, 2, 2, '2023-07-21', NULL, FALSE, 'Devolución pendiente', NULL),
(3, 3, 3, '2023-07-22', '2023-08-02', TRUE, NULL, NULL),
(4, 4, 4, '2023-07-23', NULL, FALSE, 'Devolución pendiente', NULL);

-- Crear datos de prueba para la tabla Reserva
INSERT INTO BibliotecaGranVia.Reserva (id, id_usuario, id_libro, fecha_reserva) VALUES
(1, 1, 1, '2023-07-20'),
(2, 2, 2, '2023-07-21'),
(3, 3, 3, '2023-07-22'),
(4, 4, 4, '2023-07-23');
