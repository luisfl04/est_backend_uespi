<?php 
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
?>

<?php
    try {

        header("Access-Control-Allow-Origin: *");
        header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
        header("Access-Control-Allow-Headers: Content-Type");
            
        $base_path = $_SERVER['DOCUMENT_ROOT'];
        require_once $base_path . '/backend/controller/AlunoController.php';
        require_once $base_path . '/backend/controller/ProfessorController.php';
        require_once $base_path . '/backend/controller/DisciplinaController.php';
        require_once $base_path . '/backend/controller/TurmaController.php';
    
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
            $controller = new TurmaController();
            $controller->handleRequest($method, $id);
        }
        else if ($entidade === 'disciplina') {
            $controller = new DisciplinaController();
            $controller->handleRequest($method, $id);
        }
        else if ($entidade === 'professor') {
            $controller = new ProfessorController();
            $controller->handleRequest($method, $id);
        }
        else {
            http_response_code(404);
            header('Content-Type: application/json; charset=utf-8');
            echo json_encode(["erro" => "Recurso não encontrado."]);
        }

    }
    catch(Exception $e) {
        http_response_code(500);
        header('Content-Type: application/json; charset=utf-8');
        echo json_encode(["erro" => "Erro interno: " . $e->getMessage()]);
    }

?>
