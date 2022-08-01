<?php

class Todos
{
    private $username;
    private $password;
    private $host;
    private $dbname;
    private $conn;

    function __construct($username, $password, $host, $dbname, $conn)
    {
        $this->username = $username;
        $this->password = $password;
        $this->host = $host;
        $this->dbname = $dbname;
        $this->conn = $conn;
    }


    public function connectToMySQL()
    {
        try {
            $this->conn = new PDO("mysql:host=$this->host;dbname=$this->dbname", $this->username, $this->password);
            $this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            echo "Connected successfully.. " . PHP_EOL;
            return $this->conn;
        } catch (PDOException $e) {
            echo "Connection failed: " . $e->getMessage();
        }
    }

    public function getAllTodos()
    {
        try {
            $result_count = $this->conn->query("SELECT COUNT(*) FROM todos")->fetchColumn();
            if ($result_count > 0)
            {
                $selectAllTodos = $this->conn->query("SELECT * FROM todos");

                foreach($selectAllTodos as $row) {
                    print_r("ID: " . $row["id"] . " Todo: " . $row["todo"] . " Completed: " . $row["completed"] . PHP_EOL);
                }
                $this->conn = null;
            }
        } catch (PDOException $e) {
            echo $e->getMessage();
        }
    }
}

$todos = new Todos(
    getenv("AZURE_MYSQL_DB_USERNAME"), 
    getenv("AZURE_MYSQL_DB_PASSWORD"), 
    getenv("AZURE_MYSQL_DB_HOST"), 
    getenv("AZURE_MYSQL_DB_NAME"),
    null
);

$todos->connectToMySQL();
$todos->getAllTodos();
