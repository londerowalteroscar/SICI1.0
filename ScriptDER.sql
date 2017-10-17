-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema sicidb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sicidb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sicidb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema sici
-- -----------------------------------------------------
USE `sicidb` ;

-- -----------------------------------------------------
-- Table `sicidb`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sicidb`.`usuario` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre_usuario` VARCHAR(20) NULL,
  `contrasena` VARCHAR(10) NULL,
  `nombre` VARCHAR(45) NULL,
  `dni` CHAR(10) NULL,
  `email` CHAR(20) NULL,
  `telefono` CHAR(15) NULL,
  `estado` INT(1) NULL,
  `direccion` VARCHAR(45) NULL,
  PRIMARY KEY (`id_usuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sicidb`.`responsable`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sicidb`.`responsable` (
  `id_responsable` INT(5) NOT NULL,
  `estado` INT(1) NULL,
  `nombre` VARCHAR(45) NULL,
  `direccion` VARCHAR(45) NULL,
  `dni` CHAR(10) NULL,
  `email` VARCHAR(20) NULL,
  `telefono` CHAR(15) NULL,
  PRIMARY KEY (`id_responsable`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sicidb`.`marca`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sicidb`.`marca` (
  `id_marca` INT(5) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`id_marca`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sicidb`.`unidad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sicidb`.`unidad` (
  `id_unidad` INT(5) NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(15) NULL,
  `estado` INT(1) NULL,
  PRIMARY KEY (`id_unidad`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sicidb`.`articulo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sicidb`.`articulo` (
  `id_articulo` INT(5) NOT NULL,
  `precio` DOUBLE NULL,
  `descripcion` VARCHAR(45) NULL,
  `nombre` VARCHAR(20) NULL,
  `id_unidad` INT(5) NOT NULL,
  `existencia` INT(5) NULL,
  `estock_minimo` INT(5) NULL,
  `num_serie` VARCHAR(20) NULL,
  `estado` INT(1) NULL,
  `id_marca` INT(5) NOT NULL,
  `id_empresa` INT(5) NOT NULL,
  PRIMARY KEY (`id_articulo`),
  INDEX `fk_articulo_marca_idx` (`id_marca` ASC),
  INDEX `fk_articulo_unidad_idx` (`id_unidad` ASC),
  CONSTRAINT `fk_articulo_marca`
    FOREIGN KEY (`id_marca`)
    REFERENCES `sicidb`.`marca` (`id_marca`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_articulo_unidad`
    FOREIGN KEY (`id_unidad`)
    REFERENCES `sicidb`.`unidad` (`id_unidad`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sicidb`.`almacen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sicidb`.`almacen` (
  `id_movimiento` INT(5) NOT NULL AUTO_INCREMENT,
  `id_articulo` INT(5) NOT NULL,
  `fecha_movimiento` DATETIME NULL,
  `id_responsable` INT(5) NOT NULL,
  `tipo_movimiento` INT(1) NULL,
  `cantidad_articulo` INT(5) NULL,
  `id_usuario` INT(5) NOT NULL,
  `num_transaccion` INT(5) NULL,
  PRIMARY KEY (`id_movimiento`),
  INDEX `fk_almacen_usuario1_idx` (`id_usuario` ASC),
  INDEX `fk_almacen_responsable_idx` (`id_responsable` ASC),
  INDEX `fk_almacen_articulo_idx` (`id_articulo` ASC),
  CONSTRAINT `fk_almacen_usuario`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `sicidb`.`usuario` (`id_usuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_almacen_responsable`
    FOREIGN KEY (`id_responsable`)
    REFERENCES `sicidb`.`responsable` (`id_responsable`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_almacen_articulo`
    FOREIGN KEY (`id_articulo`)
    REFERENCES `sicidb`.`articulo` (`id_articulo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sicidb`.`empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sicidb`.`empresa` (
  `id_empresa` INT(5) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `cuit` CHAR(15) NULL,
  `telefono` CHAR(15) NULL,
  `estado` INT(1) NULL,
  PRIMARY KEY (`id_empresa`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sicidb`.`empresa_has_articulo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sicidb`.`empresa_has_articulo` (
  `empresa_id_empresa` INT(5) NOT NULL,
  `articulo_id_articulo` INT(5) NOT NULL,
  PRIMARY KEY (`empresa_id_empresa`, `articulo_id_articulo`),
  INDEX `fk_empresa_has_articulo_articulo1_idx` (`articulo_id_articulo` ASC),
  INDEX `fk_empresa_has_articulo_empresa1_idx` (`empresa_id_empresa` ASC),
  CONSTRAINT `fk_empresa_has_articulo_empresa1`
    FOREIGN KEY (`empresa_id_empresa`)
    REFERENCES `sicidb`.`empresa` (`id_empresa`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_empresa_has_articulo_articulo1`
    FOREIGN KEY (`articulo_id_articulo`)
    REFERENCES `sicidb`.`articulo` (`id_articulo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
