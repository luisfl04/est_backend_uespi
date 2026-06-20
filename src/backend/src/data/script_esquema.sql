CREATE DATABASE IF NOT EXISTS crud_sistema_academico;
USE crud_sistema_academico;

CREATE TABLE Turma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    curso VARCHAR(100) NOT NULL,
    bloco_atual INT NOT NULL
);

CREATE TABLE Aluno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    turma_id INT,
    CONSTRAINT fk_aluno_turma FOREIGN KEY (turma_id) REFERENCES Turma(id) ON DELETE SET NULL
);

CREATE TABLE Professor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    formacao VARCHAR(100) NOT NULL
);

CREATE TABLE Disciplina (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    curso_relacionado VARCHAR(100) NOT NULL,
    bloco_relacionado INT NOT NULL
);