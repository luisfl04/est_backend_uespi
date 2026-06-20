<?php

class Database {

    private static $host = '127.0.0.1';
    private static $db_name = 'crud_sistema_academico';
    private static $username = 'luisfl04'; 
    private static $password = 'Senha123@';     
    private static $conn = null;

    private function __construct() {}

    public static function getConnection() {
        if (self::$conn == null) {
            try {
                $dsn = "mysql:host=" . self::$host . ";dbname=" . self::$db_name . ";charset=utf8mb4";
                self::$conn = new PDO($dsn, self::$username, self::$password);
                self::$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                self::$conn->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
                
            } catch(PDOException $exception) {
                die("Erro de conexão com o banco de dados: " . $exception->getMessage());
            }
        }
        return self::$conn;
    }

    public static function beginTransaction() {
        $conn = self::getConnection();
        if (!$conn->inTransaction()) {
            $conn->beginTransaction();
        }
    }

    public static function commit() {
        $conn = self::getConnection();
        if ($conn->inTransaction()) {
            $conn->commit();
        }
    }

    public static function rollBack() {
        $conn = self::getConnection();
        if ($conn->inTransaction()) {
            $conn->rollBack();
        }
    }
}
?>