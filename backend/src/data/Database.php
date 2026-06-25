<?php

class Database {

    private static $host = 'host_local_aqui';
    private static $db_name = 'nome_esquema_aqui';
    private static $username = 'user_ak'; 
    private static $password = 'senha_aqui';     
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