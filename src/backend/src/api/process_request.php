<?php
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
    header("Access-Control-Allow-Headers: Content-Type");

    require_once 'controller/AlunoController.php';
    require_once 'controller/TurmaController.php';
    require_once 'controller/DisciplinaController.php';
    require_once 'controller/ProfessorController.php';

    $method = $_SERVER['REQUEST_METHOD'];

    if ($method == 'OPTIONS') {
        http_response_code(200);
        exit();
    }

    $entidade = isset($_GET['entidade']) ? $_GET['entidade'] : null;
    $id = isset($_GET['id']) ? $_GET['id'] : null;

    if ($entidade === 'aluno') {
        $controller = new AlunoController();
        $controller->handleRequest($method, $id);
    } 
    else if ($entidade === 'turma') {

    }
    else if ($entidade === 'disciplina') {

    }
    else if ($entidade === 'professor') {

    }
    
    else {
        http_response_code(404);
        echo json_encode(["erro" => "Recurso não encontrado."]);
    }

?>