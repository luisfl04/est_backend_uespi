<?php
$base_path = $_SERVER['DOCUMENT_ROOT'];
require_once $base_path . '/backend/data/Database.php';

class Turma {
    private $id;
    private $curso;
    private $bloco_atual;

    // 2. Construtor recebe os parâmetros NOT NULL
    public function __construct($curso, $bloco_atual, $id = null) {
        $this->curso = $curso;
        $this->bloco_atual = $bloco_atual;
        $this->id = $id;
    }

    public function getId() { return $this->id; }
    public function setId($id) { $this->id = $id; }
    
    public function getCurso() { return $this->curso; }
    public function setCurso($curso) { $this->curso = $curso; }
    
    public function getBlocoAtual() { return $this->bloco_atual; }
    public function setBlocoAtual($bloco_atual) { $this->bloco_atual = $bloco_atual; }

    public static function getAsJson($curso, $bloco_atual) {
        $instancia = new self($curso, $bloco_atual);
        return json_encode([
            'id' => $instancia->getId(),
            'curso' => $instancia->getCurso(),
            'bloco_atual' => $instancia->getBlocoAtual()
        ]);
    }

    public function create() {
        $conn = Database::getConnection();
        $sql = "INSERT INTO Turma (curso, bloco_atual) VALUES (:curso, :bloco_atual)";
        $stmt = $conn->prepare($sql);
        $stmt->execute([
            ':curso' => $this->curso,
            ':bloco_atual' => $this->bloco_atual
        ]);
        $this->id = $conn->lastInsertId();
        return $this->id;
    }

    public static function getAll() {
        $conn = Database::getConnection();
        $stmt = $conn->query("SELECT * FROM Turma");
        return $stmt->fetchAll();
    }

    public static function getById($id) {
        $conn = Database::getConnection();
        $stmt = $conn->prepare("SELECT * FROM Turma WHERE id = :id");
        $stmt->execute([':id' => $id]);
        return $stmt->fetch();
    }

    public function update() {
        $conn = Database::getConnection();
        $sql = "UPDATE Turma SET curso = :curso, bloco_atual = :bloco_atual WHERE id = :id";
        $stmt = $conn->prepare($sql);
        return $stmt->execute([
            ':curso' => $this->curso,
            ':bloco_atual' => $this->bloco_atual,
            ':id' => $this->id
        ]);
    }

    public static function delete($id) {
        $conn = Database::getConnection();
        $stmt = $conn->prepare("DELETE FROM Turma WHERE id = :id");
        return $stmt->execute([':id' => $id]);
    }
}
?>