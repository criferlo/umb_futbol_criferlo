-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 20, 2015 at 05:28 PM
-- Server version: 5.5.41
-- PHP Version: 5.3.10-1ubuntu3.15

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `comba`
--

-- --------------------------------------------------------

--
-- Table structure for table `INTENTO`
--

CREATE TABLE IF NOT EXISTS `INTENTO` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  `fecha_at` datetime DEFAULT NULL,
  `tipo_recepcion` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_intento_tipoblanco1_idx` (`tipoblanco_id`),
  KEY `fk_intento_usuario1_idx` (`usuario_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `INTENTO`
--

INSERT INTO `INTENTO` (`id`, `velocidad_blanco`, `tamano_blanco`, `tipoblanco_id`, `velocidad_lanzadera`, `desviacion_lanzadera`, `direccion_lanzadera`, `elevacion_lanzadera`, `precision_disparo`, `velocidad_disparo`, `tiempo_respuesta`, `usuario_id`, `fecha_at`, `tipo_recepcion`) VALUES
(1, 4, 50, 1, 2000, 1.2, 3000, 3000, 4, 5, 20, 1, '2015-02-13 00:00:00', 'PIE'),
(2, 4, 50, 1, 2000, 1.2, 6000, 3000, 4, 5, 20, 1, '2015-02-18 00:00:00', 'PIE'),
(3, 4, 50, 1, 2000, 1.2, 6000, 3000, 4, 5, 20, 1, '2015-02-19 00:00:00', 'PIE'),
(4, 0, 0, 2, NULL, 0, 0, 0, 1.10444, 0, 0, 3, '2015-02-20 00:00:00', 'PIE'),
(5, 0, 0, 2, NULL, 0, 0, 0, 1.48957, 0, 0, 3, '2015-02-20 00:00:00', 'PIE'),
(6, 0, 0, 2, NULL, 0, 0, 0, 1.30059, 0, 0, 3, '2015-02-20 00:00:00', 'PIE'),
(7, 0, 0, 2, NULL, 0, 0, 0, 0.591665, 0, 0, 3, '2015-02-20 00:00:00', 'PIE'),
(8, 0, 0, 2, NULL, 0, 0, 0, 1.6165, 0, 0, 3, '2015-02-20 00:00:00', 'PIE'),
(9, 0, 0, 2, NULL, 0, 0, 0, 0.455177, 0, 0, 3, '2015-02-20 00:00:00', 'PIE'),
(10, 0, 0, 2, NULL, 0, 0, 0, 0.548162, 0, 0, 1, '2015-02-20 00:00:00', 'PIE'),
(11, 0, 0, 2, NULL, 0, 0, 0, 0.35573, 0, 0, 1, '2015-02-20 00:00:00', 'PIE'),
(12, 0, 0, 2, NULL, 0, 0, 0, 0.840715, 0, 0, 1, '2015-02-20 00:00:00', 'PIE'),
(13, 0, 0, 2, NULL, 0, 0, 0, 0.400432, 0, 0, 1, '2015-02-20 00:00:00', 'PECHO'),
(14, 0, 0, 1, NULL, 0, 0, 0, -7.09135, 0, 0, 1, '2015-02-20 00:00:00', 'MUSLO'),
(15, 0, 0, 1, NULL, 0, 0, 0, -0.365751, 0, 0, 1, '2015-02-20 00:00:00', 'MUSLO');

-- --------------------------------------------------------

--
-- Table structure for table `TIPOBLANCO`
--

CREATE TABLE IF NOT EXISTS `TIPOBLANCO` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `TIPOBLANCO`
--

INSERT INTO `TIPOBLANCO` (`id`, `nombre`) VALUES
(1, 'JUGADOR VIRTUAL'),
(2, 'JUGADOR ALEATORIO');

-- --------------------------------------------------------

--
-- Table structure for table `TIPOCIUDAD`
--

CREATE TABLE IF NOT EXISTS `TIPOCIUDAD` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `TIPOCIUDAD`
--

INSERT INTO `TIPOCIUDAD` (`id`, `nombre`) VALUES
(1, 'SANTA MARTA'),
(2, 'PASTO');

-- --------------------------------------------------------

--
-- Table structure for table `TIPOINSTITUCION`
--

CREATE TABLE IF NOT EXISTS `TIPOINSTITUCION` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `tipociudad_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tipoinstitucion_tipociudad_idx` (`tipociudad_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `TIPOINSTITUCION`
--

INSERT INTO `TIPOINSTITUCION` (`id`, `nombre`, `tipociudad_id`) VALUES
(1, 'UNIVERSIDAD MANUELA BELTRAN', 2),
(2, 'DEPORTIVO PASTO', 2),
(5, 'DEPOR CALI', 1);

-- --------------------------------------------------------

--
-- Table structure for table `TIPOSEXO`
--

CREATE TABLE IF NOT EXISTS `TIPOSEXO` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `TIPOSEXO`
--

INSERT INTO `TIPOSEXO` (`id`, `nombre`) VALUES
(1, 'MASCULINO'),
(2, 'FEMENINO');

-- --------------------------------------------------------

--
-- Table structure for table `USUARIO`
--

CREATE TABLE IF NOT EXISTS `USUARIO` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cedula` varchar(45) NOT NULL,
  `nombre_completo` varchar(200) NOT NULL,
  `fecha_nacimiento` datetime NOT NULL,
  `estatura` int(11) DEFAULT NULL,
  `peso` int(11) DEFAULT NULL,
  `fecha_registro_at` datetime NOT NULL,
  `tipoinstitucion_id` int(11) NOT NULL,
  `tiposexo_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuario_tipoinstitucion1_idx` (`tipoinstitucion_id`),
  KEY `fk_usuario_tiposexo1_idx` (`tiposexo_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `USUARIO`
--

INSERT INTO `USUARIO` (`id`, `cedula`, `nombre_completo`, `fecha_nacimiento`, `estatura`, `peso`, `fecha_registro_at`, `tipoinstitucion_id`, `tiposexo_id`) VALUES
(1, '87069371', 'CRISTHIAN FERNANDO LOMBANA', '2015-02-13 00:00:00', 175, 30, '2015-02-13 00:00:00', 1, 1),
(3, '909090', 'WILLAIAM', '2013-02-14 00:00:00', 142, 76, '2015-02-20 00:00:00', 1, 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `INTENTO`
--
ALTER TABLE `INTENTO`
  ADD CONSTRAINT `fk_intento_tipoblanco1` FOREIGN KEY (`tipoblanco_id`) REFERENCES `TIPOBLANCO` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_intento_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `USUARIO` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `TIPOINSTITUCION`
--
ALTER TABLE `TIPOINSTITUCION`
  ADD CONSTRAINT `fk_tipoinstitucion_tipociudad` FOREIGN KEY (`tipociudad_id`) REFERENCES `TIPOCIUDAD` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `USUARIO`
--
ALTER TABLE `USUARIO`
  ADD CONSTRAINT `fk_usuario_tipoinstitucion1` FOREIGN KEY (`tipoinstitucion_id`) REFERENCES `TIPOINSTITUCION` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuario_tiposexo1` FOREIGN KEY (`tiposexo_id`) REFERENCES `TIPOSEXO` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
