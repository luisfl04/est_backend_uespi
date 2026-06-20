<?php

$base_path = $_SERVER['DOCUMENT_ROOT'];
require_once $base_path . '/backend/model/Disciplina.php';

class DisciplinaController {

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
        $this->jsonResponse(Disciplina::getAll(), 200);
    }

    private function getById($id) {
        $disciplina = Disciplina::getById($id);
        $disciplina ? $this->jsonResponse($disciplina, 200) : $this->jsonResponse(["erro" => "Disciplina não encontrada."], 404);
    }

    private function create() {
        $dados = $this->getJsonInput();

        if (!isset($dados['nome']) || !isset($dados['curso_relacionado']) || !isset($dados['bloco_relacionado'])) {
            $this->jsonResponse(["erro" => "Os campos 'nome', 'curso_relacionado' e 'bloco_relacionado' são obrigatórios."], 400);
        }

        $disciplina = new Disciplina($dados['nome'], $dados['curso_relacionado'], $dados['bloco_relacionado']);
        
        $idCriado = $disciplina->create();
        $this->jsonResponse(["mensagem" => "Disciplina criada com sucesso.", "id" => $idCriado], 201);
    }

    private function update($id) {
        $dados = $this->getJsonInput();
        
        if (!Disciplina::getById($id)) {
            $this->jsonResponse(["erro" => "Disciplina não encontrada."], 404);
        }

        if (!isset($dados['nome']) || !isset($dados['curso_relacionado']) || !isset($dados['bloco_relacionado'])) {
            $this->jsonResponse(["erro" => "Os campos 'nome', 'curso_relacionado' e 'bloco_relacionado' são obrigatórios."], 400);
        }

        $disciplina = new Disciplina($dados['nome'], $dados['curso_relacionado'], $dados['bloco_relacionado'], $id);
        
        if ($disciplina->update()) {
            $this->jsonResponse(["mensagem" => "Disciplina atualizada com sucesso."], 200);
        } else {
            $this->jsonResponse(["erro" => "Falha ao atualizar a disciplina."], 500);
        }
    }

    private function delete($id) {
        if (!Disciplina::getById($id)) {
            $this->jsonResponse(["erro" => "Disciplina não encontrada."], 404);
        }

        if (Disciplina::delete($id)) {
            $this->jsonResponse(["mensagem" => "Disciplina deletada com sucesso."], 200);
        } else {
            $this->jsonResponse(["erro" => "Falha ao deletar a disciplina."], 500);
        }
    }
}
?>