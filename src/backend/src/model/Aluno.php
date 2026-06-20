<?php
$base_path = $_SERVER['DOCUMENT_ROOT'];
require_once $base_path . '/backend/data/Database.php';

class Aluno {
    private $id;
    private $nome;
    private $email;
    private $telefone;
    private $turma_id;

    public function __construct($nome, $email, $telefone = null, $turma_id = null, $id = null) {
        $this->nome = $nome;
        $this->email = $email;
        $this->telefone = $telefone;
        $this->turma_id = $turma_id;
        $this->id = $id;
    }

    public function getId() { return $this->id; }
    public function setId($id) { $this->id = $id; }

    public function getNome() { return $this->nome; }
    public function setNome($nome) { $this->nome = $nome; }

    public function getEmail() { return $this->email; }
    public function setEmail($email) { $this->email = $email; }

    public function getTelefone() { return $this->telefone; }
    public function setTelefone($telefone) { $this->telefone = $telefone; }

    public function getTurmaId() { return $this->turma_id; }
    public function setTurmaId($turma_id) { $this->turma_id = $turma_id; }

    public static function getAsJson($nome, $email, $telefone = null, $turma_id = null) {
        $instancia = new self($nome, $email, $telefone, $turma_id);
        return json_encode([
            'id' => $instancia->getId(),
            'nome' => $instancia->getNome(),
            'email' => $instancia->getEmail(),
            'telefone' => $instancia->getTelefone(),
            'turma_id' => $instancia->getTurmaId()
        ]);
    }

    public function create() {
        $conn = Database::getConnection();
        $sql = "INSERT INTO Aluno (nome, email, telefone, turma_id) VALUES (:nome, :email, :telefone, :turma_id)";
        $stmt = $conn->prepare($sql);
        $stmt->execute([
            ':nome' => $this->nome,
            ':email' => $this->email,
            ':telefone' => $this->telefone,
            ':turma_id' => $this->turma_id
        ]);
        $this->id = $conn->lastInsertId();
        return $this->id;
    }

    public static function getAll() {
        $conn = Database::getConnection();
        $stmt = $conn->query("SELECT * FROM Aluno");
        return $stmt->fetchAll();
    }

    public static function getById($id) {
        $conn = Database::getConnection();
        $stmt = $conn->prepare("SELECT * FROM Aluno WHERE id = :id");
        $stmt->execute([':id' => $id]);
        return $stmt->fetch();
    }

    public function update() {
        $conn = Database::getConnection();
        $sql = "UPDATE Aluno SET nome = :nome, email = :email, telefone = :telefone, turma_id = :turma_id WHERE id = :id";
        $stmt = $conn->prepare($sql);
        return $stmt->execute([
            ':nome' => $this->nome,
            ':email' => $this->email,
            ':telefone' => $this->telefone,
            ':turma_id' => $this->turma_id,
            ':id' => $this->id
        ]);
    }

    public static function delete($id) {
        $conn = Database::getConnection();
        $stmt = $conn->prepare("DELETE FROM Aluno WHERE id = :id");
        return $stmt->execute([':id' => $id]);
    }
}
?>