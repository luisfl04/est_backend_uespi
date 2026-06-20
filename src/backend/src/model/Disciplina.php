<?php
require_once 'data/Database.php';

class Disciplina {
    private $id;
    private $nome;
    private $curso_relacionado;
    private $bloco_relacionado;

    public function __construct($nome, $curso_relacionado, $bloco_relacionado, $id = null) {
        $this->nome = $nome;
        $this->curso_relacionado = $curso_relacionado;
        $this->bloco_relacionado = $bloco_relacionado;
        $this->id = $id;
    }

    public function getId() { return $this->id; }
    public function setId($id) { $this->id = $id; }
    public function getNome() { return $this->nome; }
    public function setNome($nome) { $this->nome = $nome; }
    public function getCursoRelacionado() { return $this->curso_relacionado; }
    public function setCursoRelacionado($curso) { $this->curso_relacionado = $curso; }
    public function getBlocoRelacionado() { return $this->bloco_relacionado; }
    public function setBlocoRelacionado($bloco) { $this->bloco_relacionado = $bloco; }

    public static function getAsJson($nome, $curso_relacionado, $bloco_relacionado) {
        $instancia = new self($nome, $curso_relacionado, $bloco_relacionado);
        return json_encode([
            'id' => $instancia->getId(),
            'nome' => $instancia->getNome(),
            'curso_relacionado' => $instancia->getCursoRelacionado(),
            'bloco_relacionado' => $instancia->getBlocoRelacionado()
        ]);
    }

    public function create() {
        $conn = Database::getConnection();
        $sql = "INSERT INTO Disciplina (nome, curso_relacionado, bloco_relacionado) VALUES (:nome, :curso, :bloco)";
        $stmt = $conn->prepare($sql);
        $stmt->execute([
            ':nome' => $this->nome,
            ':curso' => $this->curso_relacionado,
            ':bloco' => $this->bloco_relacionado
        ]);
        $this->id = $conn->lastInsertId();
        return $this->id;
    }

    public static function getAll() {
        $conn = Database::getConnection();
        $stmt = $conn->query("SELECT * FROM Disciplina");
        return $stmt->fetchAll();
    }

    public static function getById($id) {
        $conn = Database::getConnection();
        $stmt = $conn->prepare("SELECT * FROM Disciplina WHERE id = :id");
        $stmt->execute([':id' => $id]);
        return $stmt->fetch();
    }

    public function update() {
        $conn = Database::getConnection();
        $sql = "UPDATE Disciplina SET nome = :nome, curso_relacionado = :curso, bloco_relacionado = :bloco WHERE id = :id";
        $stmt = $conn->prepare($sql);
        return $stmt->execute([
            ':nome' => $this->nome,
            ':curso' => $this->curso_relacionado,
            ':bloco' => $this->bloco_relacionado,
            ':id' => $this->id
        ]);
    }

    public static function delete($id) {
        $conn = Database::getConnection();
        $stmt = $conn->prepare("DELETE FROM Disciplina WHERE id = :id");
        return $stmt->execute([':id' => $id]);
    }
}
?>