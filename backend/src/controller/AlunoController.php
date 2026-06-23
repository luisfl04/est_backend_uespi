<?php

$base_path = $_SERVER['DOCUMENT_ROOT'];
require_once $base_path . '/backend/model/Aluno.php';

class AlunoController {

    private function jsonResponse($data, $statusCode = 200) {
        http_response_code($statusCode);
        header('Content-Type: application/json; charset=utf-8');
        echo json_encode($data);
        exit;
    }
    private function getJsonInput() {
        try {
            $body = file_get_contents("php://input");
            return json_decode($body, true);
        }
        catch(Exception $e) {
            $this->jsonResponse([
                "mensagem" => "Erro ao tratar os dados", 
            ], 500);
        }
    }

    public function handleRequest($method, $id = null) {
        try {
            switch ($method) {
                case 'GET':
                    if ($id) {
                        $this->getById($id);
                    } else {
                        $this->getAll();
                    }
                    break;
                case 'POST':
                    $this->create();
                    break;
                case 'PUT':
                    if (!$id) {
                        $this->jsonResponse(["erro" => "ID não fornecido para atualização."], 400);
                    }
                    $this->update($id);
                    break;
                case 'DELETE':
                    if (!$id) {
                        $this->jsonResponse(["erro" => "ID não fornecido para deleção."], 400);
                    }
                    $this->delete($id);
                    break;
                default:
                    $this->jsonResponse(["erro" => "Método HTTP não suportado."], 405);
                    break;
            }
        } catch (Exception $e) {
            $this->jsonResponse(["erro" => "Erro interno no servidor: " . $e->getMessage()], 500);
        }
    }

    private function getAll() {
        $alunos = Aluno::getAll();
        $this->jsonResponse($alunos, 200);
    }

    private function getById($id) {
        $aluno = Aluno::getById($id);
        if ($aluno) {
            $this->jsonResponse($aluno, 200);
        } else {
            $this->jsonResponse(["erro" => "Aluno não encontrado."], 404);
        }
    }

    private function create() {
        $dados = $this->getJsonInput();
        
        if (!isset($dados['nome']) || !isset($dados['email'])) {
            $this->jsonResponse(["erro" => "Os campos 'nome' e 'email' são obrigatórios."], 400);
        }

        $telefone = isset($dados['telefone']) ? $dados['telefone'] : null;
        $turma_id = isset($dados['turma_id']) ? $dados['turma_id'] : null;

        $aluno = new Aluno($dados['nome'], $dados['email'], $telefone, $turma_id);
        
        try {
            $idCriado = $aluno->create();
            $this->jsonResponse([
                "mensagem" => "Aluno criado com sucesso.", 
                "id" => $idCriado
            ], 201);
        } catch (PDOException $e) {

            if ($e->getCode() == 23000) { 
                $this->jsonResponse(["erro" => "Erro ao cadastrar dados, verifique os dados informados e tente novamente"], 409);
            }
            throw $e;
        }
    }

    private function update($id) {
        $dados = $this->getJsonInput();
        
        $alunoExistente = Aluno::getById($id);
        if (!$alunoExistente) {
            $this->jsonResponse(["erro" => "Aluno não encontrado para atualização."], 404);
        }

        if (!isset($dados['nome']) || !isset($dados['email'])) {
            $this->jsonResponse(["erro" => "Os campos 'nome' e 'email' são obrigatórios."], 400);
        }

        $telefone = isset($dados['telefone']) ? $dados['telefone'] : null;
        $turma_id = isset($dados['turma_id']) ? $dados['turma_id'] : null;

        $aluno = new Aluno($dados['nome'], $dados['email'], $telefone, $turma_id, $id);
        
        try {
            $sucesso = $aluno->update();
            if ($sucesso) {
                $this->jsonResponse(["mensagem" => "Aluno atualizado com sucesso."], 200);
            } else {
                $this->jsonResponse(["erro" => "Falha ao atualizar o aluno."], 500);
            }
        } catch (PDOException $e) {
            if ($e->getCode() == 23000) { 
                $this->jsonResponse([
                    "erro" => "Erro ao processar os dados, verifique e tente novamente.",
                    "descricao" => $e->getMessage()
                ], 500);
            }
            throw $e;
        }
    }

    private function delete($id) {
        $alunoExistente = Aluno::getById($id);
        if (!$alunoExistente) {
            $this->jsonResponse(["erro" => "Aluno não encontrado para deleção."], 404);
        }

        $sucesso = Aluno::delete($id);
        if ($sucesso) {
            $this->jsonResponse(["mensagem" => "Aluno deletado com sucesso."], 200);
        } else {
            $this->jsonResponse(["erro" => "Falha ao deletar o aluno."], 500);
        }
    }
}
?>