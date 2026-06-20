<?php
$base_path = $_SERVER['DOCUMENT_ROOT'];
require_once $base_path . '/backend/data/Database.php';

class Professor {
    private $id;
    private $nome;
    private $email;
    private $telefone;
    private $formacao;

    public function __construct($nome, $email, $formacao, $telefone = null, $id = null) {
        $this->nome = $nome;
        $this->email = $email;
        $this->formacao = $formacao;
        $this->telefone = $telefone;
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
    public function getFormacao() { return $this->formacao; }
    public function setFormacao($formacao) { $this->formacao = $formacao; }

    public static function getAsJson($nome, $email, $formacao, $telefone = null) {
        $instancia = new self($nome, $email, $formacao, $telefone);
        return json_encode([
            'id' => $instancia->getId(),
            'nome' => $instancia->getNome(),
            'email' => $instancia->getEmail(),
            'telefone' => $instancia->getTelefone(),
            'formacao' => $instancia->getFormacao()
        ]);
    }

    public function create() {
        $conn = Database::getConnection();
        $sql = "INSERT INTO Professor (nome, email, telefone, formacao) VALUES (:nome, :email, :telefone, :formacao)";
        $stmt = $conn->prepare($sql);
        $stmt->execute([
            ':nome' => $this->nome,
            ':email' => $this->email,
            ':telefone' => $this->telefone,
            ':formacao' => $this->formacao
        ]);
        $this->id = $conn->lastInsertId();
        return $this->id;
    }

    public static function getAll() {
        $conn = Database::getConnection();
        $stmt = $conn->query("SELECT * FROM Professor");
        return $stmt->fetchAll();
    }

    public static function getById($id) {
        $conn = Database::getConnection();
        $stmt = $conn->prepare("SELECT * FROM Professor WHERE id = :id");
        $stmt->execute([':id' => $id]);
        return $stmt->fetch();
    }

    public function update() {
        $conn = Database::getConnection();
        $sql = "UPDATE Professor SET nome = :nome, email = :email, telefone = :telefone, formacao = :formacao WHERE id = :id";
        $stmt = $conn->prepare($sql);
        return $stmt->execute([
            ':nome' => $this->nome,
            ':email' => $this->email,
            ':telefone' => $this->telefone,
            ':formacao' => $this->formacao,
            ':id' => $this->id
        ]);
    }

    public static function delete($id) {
        $conn = Database::getConnection();
        $stmt = $conn->prepare("DELETE FROM Professor WHERE id = :id");
        return $stmt->execute([':id' => $id]);
    }
}
?>