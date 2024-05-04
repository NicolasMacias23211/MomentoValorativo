-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema school
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema school
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `school` DEFAULT CHARACTER SET utf8 ;
USE `school` ;

-- -----------------------------------------------------
-- Table `school`.`students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school`.`students` (
  `document` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `age` INT NOT NULL,
  `createdAt` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `deletedAt` DATETIME NULL,
  PRIMARY KEY (`document`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school`.`teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school`.`teacher` (
  `teacherDocument` INT NOT NULL,
  `teacherName` VARCHAR(45) NOT NULL,
  `deletedat` DATETIME NULL,
  `createdAt` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`teacherDocument`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school`.`courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school`.`courses` (
  `courseCode` INT NOT NULL,
  `courseName` VARCHAR(45) NOT NULL,
  `couserDescription` VARCHAR(45) NOT NULL,
  `createdAt` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updateAt` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `deleteAt` DATETIME NULL,
  `teacher_teacherDocument` INT NOT NULL,
  PRIMARY KEY (`courseCode`),
  INDEX `fk_courses_teacher_idx` (`teacher_teacherDocument` ASC) VISIBLE,
  CONSTRAINT `fk_courses_teacher`
    FOREIGN KEY (`teacher_teacherDocument`)
    REFERENCES `school`.`teacher` (`teacherDocument`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school`.`studentsXCurses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school`.`studentsXCurses` (
  `id` INT NOT NULL,
  `students_document` INT NOT NULL,
  `courses_courseCode` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_studentsXCurses_students1_idx` (`students_document` ASC) VISIBLE,
  INDEX `fk_studentsXCurses_courses1_idx` (`courses_courseCode` ASC) VISIBLE,
  CONSTRAINT `fk_studentsXCurses_students1`
    FOREIGN KEY (`students_document`)
    REFERENCES `school`.`students` (`document`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_studentsXCurses_courses1`
    FOREIGN KEY (`courses_courseCode`)
    REFERENCES `school`.`courses` (`courseCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


----------------------------------------------------------
-- SQL minimal data
-------------------------------------------------------------
INSERT INTO `school`.`students` (`document`, `name`, `lastname`, `age`, `deletedAt`)
VALUES
(63201, 'Juan', 'Pérez', 20, NULL),
(67890, 'María', 'López', 22, NULL);

INSERT INTO `school`.`teacher` (`teacherDocument`, `teacherName`, `deletedat`)
VALUES
(112233, 'Carlos Ruiz', NULL),
(445566, 'Ana Torres',  NULL);

INSERT INTO `school`.`courses` (`courseCode`, `courseName`, `couserDescription`, `deleteAt`, `teacher_teacherDocument`)
VALUES
(101, 'Matemáticas', 'Curso básico de matemáticas', NULL, 112233),
(102, 'Historia', 'Curso intermedio de historia', NULL, 445566);

INSERT INTO `school`.`studentsXCurses` (`id`, `students_document`, `courses_courseCode`)
VALUES
(1, 63201, 101),
(2, 67890, 102);