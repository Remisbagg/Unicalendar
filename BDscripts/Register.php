<?php
$con= mysqli_connect("localhost","root","","usuarios");

$name = $_POST["name"];
$email = $_POST["email"];
$username = $_POST["username"];
$password = $_POST["password"];
$statement = mysqli_prepare($con, "INSERT INTO user (name, email, username, password) VALUES (?, ?, ?, ?)");
mysqli_stmt_bind_param($statement, "siss", $name, $email, $username, $password);
mysqli_stmt_execute($statement);

$response=array();
$response["success"]=true;

echo json_encode($response);
?>