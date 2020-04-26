<?php
	$data = json_decode(file_get_contents('php://input'), true);
	//$EMAIL=$_POST["email"];
	//$CLAVE=$_POST["clave"];
	$EMAIL=$data["email"];
	$CLAVE=$data["clave"];
	//echo "$email";
	//$EMAIL="andrea@email.com";
	//$CLAVE="1234";
	$con=mysqli_connect("localhost","andrea","1234","neumaps2");

	$statement=mysqli_prepare($con,"SELECT * FROM users WHERE email= ? AND clave= ?");
	mysqli_stmt_bind_param($statement,"ss",$EMAIL,$CLAVE);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement,$id,$email,$clave,$nombre,$edad,$telefono,$direccion,$provincia,$medico);
	$response=array();
	$response["success"]= false;

	while(mysqli_stmt_fetch($statement)){
		$response["id"]=$id;
		$response["success"]=true;
		$response["email"]=$email;
		$response["clave"]=$clave;
		$response["nombre"]=$nombre;
		$response["edad"]=$edad;
		$response["telefono"]=$telefono;
		$response["direccion"]=$direccion;
		$response["provincia"]=$provincia;
		$response["medico"]=$medico;
	}
	
	$json = json_encode($response);
	//echo json_encode($response);
?>