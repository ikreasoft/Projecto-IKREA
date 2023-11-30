CREATE TABLE `conexion1`.`autor` (
  `autor_id` bigint NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`autor_id`)
);
INSERT INTO `conexion1`.`autor` ( `fecha_nacimiento`, `nombre`) VALUES ('1873-06-08', 'Azorin');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('Ganivet', '1865-12-13', 'Ángel');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('Machado', '1875-07-26', 'Antonio');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('Arniches', '1866-10-11', 'Carlos');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('Bayo', '1859-04-16', 'Ciro');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('Benavente', '1866-08-12', 'Jacinto');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('Gabriel y Galán', '1870-06-28', 'Jose María');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('Machado', '1874-08-29', 'Manuel');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('de Unamuno', '1864-09-29', 'Migel');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('Baroja', '1872-12-28', 'Pío');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('de Maeztu', '1875-05-04', 'Ramiro');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('Menéndez Pidal', '1869-03-13', 'Ramón');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('del Valle-Inclán', '1866-10-28', 'Ramón María');
INSERT INTO `conexion1`.`autor` (`apellidos`, `fecha_nacimiento`, `nombre`) VALUES ('Blasco Ibáñez', '1867-01-29', 'Vicente');
CREATE TABLE `conexion1`.`titulo` (
  `titulo_id` bigint NOT NULL AUTO_INCREMENT,
  `isbn` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `num_reservas` int NOT NULL,
  PRIMARY KEY (`titulo_id`)
);
CREATE TABLE `conexion1`.`titulo_autor` (
  `titulo_id` bigint NOT NULL,
  `autor_id` bigint NOT NULL,
  KEY `FK6oj3145xwln96pau8ns5sako6` (`autor_id`),
  KEY `FKbvpxp9iu7leig1x8gg4r3ih9b` (`titulo_id`),
  CONSTRAINT `FK6oj3145xwln96pau8ns5sako6` FOREIGN KEY (`autor_id`) REFERENCES `autor` (`autor_id`),
  CONSTRAINT `FKbvpxp9iu7leig1x8gg4r3ih9b` FOREIGN KEY (`titulo_id`) REFERENCES `titulo` (`titulo_id`)
);
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8184-2', 'La voluntad', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8185-9', 'Cartas finlandesas', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8186-6', 'Juan de Mairena', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8187-3', 'La Lola se va a los puertos', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8188-0', 'Los caciques', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8189-7', 'De Barcelona a La Habana', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8190-3', 'Lo cursi', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8191-0', 'Gabriel y Galán. Poesía', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8192-7', 'Las adelfas', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8193-4', 'La tía Tula', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8194-1', 'La lucha por la vida', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8195-8', 'Frente a la República', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8196-5', 'La España del Cid', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8197-2', 'Luces de bohemia', '0');
INSERT INTO `conexion1`.`titulo` (`isbn`, `nombre`, `num_reservas`) VALUES ('978-84-206-8198-9', 'La barraca', '0');


INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('1', '1');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('2', '2');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('3', '3');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('3', '4');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('8', '4');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('4', '5');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('5', '6');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('6', '7');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('7', '8');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('8', '9');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('9', '10');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('10', '11');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('11', '12');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('12', '13');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('13', '14');
INSERT INTO `conexion1`.`titulo_autor` (`autor_id`, `titulo_id`) VALUES ('14', '15');
```


