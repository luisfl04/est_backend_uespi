<?php
$base_path = $_SERVER['DOCUMENT_ROOT'];
require_once $base_path . '/backend/model/Turma.php';

class TurmaController {

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
        $this->jsonResponse(Turma::getAll(), 200);
    }

    private function getById($id) {
        $turma = Turma::getById($id);
        $turma ? $this->jsonResponse($turma, 200) : $this->jsonResponse(["erro" => "Turma não encontrada."], 404);
    }

    private function create() {
        $dados = $this->getJsonInput();

        if (!isset($dados['curso']) || !isset($dados['bloco_atual'])) {
            $this->jsonResponse(["erro" => "Os campos 'curso' e 'bloco_atual' são obrigatórios."], 400);
        }

        $turma = new Turma($dados['curso'], $dados['bloco_atual']);
        
        $idCriado = $turma->create();
        $this->jsonResponse(["mensagem" => "Turma criada com sucesso.", "id" => $idCriado], 201);
    }

    private function update($id) {
        $dados = $this->getJsonInput();
        
        if (!Turma::getById($id)) {
            $this->jsonResponse(["erro" => "Turma não encontrada."], 404);
        }

        if (!isset($dados['curso']) || !isset($dados['bloco_atual'])) {
            $this->jsonResponse(["erro" => "Os campos 'curso' e 'bloco_atual' são obrigatórios."], 400);
        }

        $turma = new Turma($dados['curso'], $dados['bloco_atual'], $id);
        
        if ($turma->update()) {
            $this->jsonResponse(["mensagem" => "Turma atualizada com sucesso."], 200);
        } else {
            $this->jsonResponse(["erro" => "Falha ao atualizar a turma."], 500);
        }
    }

    private function delete($id) {
        if (!Turma::getById($id)) {
            $this->jsonResponse(["erro" => "Turma não encontrada."], 404);
        }

        if (Turma::delete($id)) {
            $this->jsonResponse(["mensagem" => "Turma deletada com sucesso."], 200);
        } else {
            $this->jsonResponse(["erro" => "Falha ao deletar a turma."], 500);
        }
    }
}
?>