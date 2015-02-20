-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-02-2015 a las 19:03:01
-- Versión del servidor: 5.6.21
-- Versión de PHP: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `comba`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `intento`
--

CREATE TABLE IF NOT EXISTS `intento` (
`id` int(11) NOT NULL,
  `velocidad_blanco` float DEFAULT NULL,
  `tamano_blanco` float DEFAULT NULL,
  `tipoblanco_id` int(11) NOT NULL,
  `velocidad_lanzadera` int(11) DEFAULT NULL,
  `desviacion_lanzadera` float DEFAULT NULL,
  `direccion_lanzadera` float DEFAULT NULL,
  `elevacion_lanzadera` float DEFAULT NULL,
  `precision_disparo` float DEFAULT NULL,
  `velocidad_disparo` float DEFAULT NULL,
  `tiempo_respuesta` float DEFAULT NULL,
  `usuario_id` int(11) NOT NULL,
  `fecha_at` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `intento`
--

INSERT INTO `intento` (`id`, `velocidad_blanco`, `tamano_blanco`, `tipoblanco_id`, `velocidad_lanzadera`, `desviacion_lanzadera`, `direccion_lanzadera`, `elevacion_lanzadera`, `precision_disparo`, `velocidad_disparo`, `tiempo_respuesta`, `usuario_id`, `fecha_at`) VALUES
(1, 4, 50, 1, 2000, 1.2, 3000, 3000, 4, 5, 20, 1, '2015-02-13 00:00:00'),
(2, 4, 50, 1, 2000, 1.2, 6000, 3000, 4, 5, 20, 1, '2015-02-18 00:00:00'),
(3, 4, 50, 1, 2000, 1.2, 6000, 3000, 4, 5, 20, 1, '2015-02-19 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoblanco`
--

CREATE TABLE IF NOT EXISTS `tipoblanco` (
`id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipoblanco`
--

INSERT INTO `tipoblanco` (`id`, `nombre`) VALUES
(1, 'JUGADOR VIRTUAL'),
(2, 'JUGADOR ALEATORIO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipociudad`
--

CREATE TABLE IF NOT EXISTS `tipociudad` (
`id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipociudad`
--

INSERT INTO `tipociudad` (`id`, `nombre`) VALUES
(1, 'SANTA MARTA'),
(2, 'PASTO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoinstitucion`
--

CREATE TABLE IF NOT EXISTS `tipoinstitucion` (
`id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `tipociudad_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipoinstitucion`
--

INSERT INTO `tipoinstitucion` (`id`, `nombre`, `tipociudad_id`) VALUES
(1, 'UNIVERSIDAD MANUELA BELTRAN', 2),
(2, 'DEPORTIVO PASTO', 2),
(5, 'DEPOR CALI', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiposexo`
--

CREATE TABLE IF NOT EXISTS `tiposexo` (
`id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tiposexo`
--

INSERT INTO `tiposexo` (`id`, `nombre`) VALUES
(1, 'MASCULINO'),
(2, 'FEMENINO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
`id` int(11) NOT NULL,
  `cedula` varchar(45) NOT NULL,
  `nombre_completo` varchar(200) NOT NULL,
  `fecha_nacimiento` datetime NOT NULL,
  `estatura` int(11) DEFAULT NULL,
  `peso` int(11) DEFAULT NULL,
  `fecha_registro_at` datetime NOT NULL,
  `tipoinstitucion_id` int(11) NOT NULL,
  `tiposexo_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `cedula`, `nombre_completo`, `fecha_nacimiento`, `estatura`, `peso`, `fecha_registro_at`, `tipoinstitucion_id`, `tiposexo_id`) VALUES
(1, '87069371', 'CRISTHIAN FERNANDO LOMBANA', '2015-02-13 00:00:00', 175, 30, '2015-02-13 00:00:00', 1, 1),
(3, '909090', 'WILLAIAM', '2013-02-14 00:00:00', 142, 76, '2015-02-20 00:00:00', 1, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `intento`
--
ALTER TABLE `intento`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_intento_tipoblanco1_idx` (`tipoblanco_id`), ADD KEY `fk_intento_usuario1_idx` (`usuario_id`);

--
-- Indices de la tabla `tipoblanco`
--
ALTER TABLE `tipoblanco`
 ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipociudad`
--
ALTER TABLE `tipociudad`
 ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipoinstitucion`
--
ALTER TABLE `tipoinstitucion`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_tipoinstitucion_tipociudad_idx` (`tipociudad_id`);

--
-- Indices de la tabla `tiposexo`
--
ALTER TABLE `tiposexo`
 ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_usuario_tipoinstitucion1_idx` (`tipoinstitucion_id`), ADD KEY `fk_usuario_tiposexo1_idx` (`tiposexo_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `intento`
--
ALTER TABLE `intento`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `tipoblanco`
--
ALTER TABLE `tipoblanco`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `tipociudad`
--
ALTER TABLE `tipociudad`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `tipoinstitucion`
--
ALTER TABLE `tipoinstitucion`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `tiposexo`
--
ALTER TABLE `tiposexo`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `intento`
--
ALTER TABLE `intento`
ADD CONSTRAINT `fk_intento_tipoblanco1` FOREIGN KEY (`tipoblanco_id`) REFERENCES `tipoblanco` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_intento_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tipoinstitucion`
--
ALTER TABLE `tipoinstitucion`
ADD CONSTRAINT `fk_tipoinstitucion_tipociudad` FOREIGN KEY (`tipociudad_id`) REFERENCES `tipociudad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
ADD CONSTRAINT `fk_usuario_tipoinstitucion1` FOREIGN KEY (`tipoinstitucion_id`) REFERENCES `tipoinstitucion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_usuario_tiposexo1` FOREIGN KEY (`tiposexo_id`) REFERENCES `tiposexo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
