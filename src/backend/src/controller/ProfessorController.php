<?php
$base_path = $_SERVER['DOCUMENT_ROOT'];
require_once $base_path . '/backend/model/Professor.php';

class ProfessorController {

    private function jsonResponse($data, $statusCode = 200) {
        http_response_code($statusCode);
        header('Content-Type: application/json; charset=utf-8');
        echo json_encode($data);
        exit;
    }

    private function getJsonInput() {
        return json_decode(file_get_contents("php://input"), true);
    }

    public function handleRequest($method, $id = null) {
        try {
            switch ($method) {
                case 'GET':
                    $id ? $this->getById($id) : $this->getAll();
                    break;
                case 'POST':
                    $this->create();
                    break;
                case 'PUT':
                    if (!$id) $this->jsonResponse(["erro" => "ID não fornecido."], 400);
                    $this->update($id);
                    break;
                case 'DELETE':
                    if (!$id) $this->jsonResponse(["erro" => "ID não fornecido."], 400);
                    $this->delete($id);
                    break;
                default:
                    $this->jsonResponse(["erro" => "Método não suportado."], 405);
            }
        } catch (Exception $e) {
            $this->jsonResponse(["erro" => "Erro no servidor: " . $e->getMessage()], 500);
        }
    }

    private function getAll() {
        $this->jsonResponse(Professor::getAll(), 200);
    }

    private function getById($id) {
        $professor = Professor::getById($id);
        $professor ? $this->jsonResponse($professor, 200) : $this->jsonResponse(["erro" => "Professor não encontrado."], 404);
    }

    private function create() {
        $dados = $this->getJsonInput();

        if (!isset($dados['nome']) || !isset($dados['email']) || !isset($dados['formacao'])) {
            $this->jsonResponse(["erro" => "Os campos 'nome', 'email' e 'formacao' são obrigatórios."], 400);
        }

        $telefone = isset($dados['telefone']) ? $dados['telefone'] : null;
        $professor = new Professor($dados['nome'], $dados['email'], $dados['formacao'], $telefone);
        
        try {
            $idCriado = $professor->create();
            $this->jsonResponse(["mensagem" => "Professor criado com sucesso.", "id" => $idCriado], 201);
        } catch (PDOException $e) {
            if ($e->getCode() == 23000) { 
                $this->jsonResponse(["erro" => "Este e-mail já está cadastrado para outro professor."], 409);
            }
            throw $e;
        }
    }

    private function update($id) {
        $dados = $this->getJsonInput();
        
        if (!Professor::getById($id)) {
            $this->jsonResponse(["erro" => "Professor não encontrado."], 404);
        }

        if (!isset($dados['nome']) || !isset($dados['email']) || !isset($dados['formacao'])) {
            $this->jsonResponse(["erro" => "Os campos 'nome', 'email' e 'formacao' são obrigatórios."], 400);
        }

        $telefone = isset($dados['telefone']) ? $dados['telefone'] : null;
        $professor = new Professor($dados['nome'], $dados['email'], $dados['formacao'], $telefone, $id);
        
        try {
            if ($professor->update()) {
                $this->jsonResponse(["mensagem" => "Professor atualizado com sucesso."], 200);
            } else {
                $this->jsonResponse(["erro" => "Falha ao atualizar o professor."], 500);
            }
        } catch (PDOException $e) {
            if ($e->getCode() == 23000) { 
                $this->jsonResponse(["erro" => "Este e-mail já está em uso."], 409);
            }
            throw $e;
        }
    }

    private function delete($id) {
        if (!Professor::getById($id)) {
            $this->jsonResponse(["erro" => "Professor não encontrado."], 404);
        }

        if (Professor::delete($id)) {
            $this->jsonResponse(["mensagem" => "Professor deletado com sucesso."], 200);
        } else {
            $this->jsonResponse(["erro" => "Falha ao deletar o professor."], 500);
        }
    }
}
?>