<?php
	$con=mysqli_connect("%","andrea","1234","neumaps2");
	$EMAIL=$_POST["email"];
	$CLAVE=$_POST["clave"];
	$NOMBRE=$_POST["nombre"];
	$EDAD=$_POST["edad"];
	$TELEFONO=$_POST["telefono"];
	$DIRECCION=$_POST["direccion"];
	$PROVINCIA=$_POST["provincia"];
	$MEDICO=$_POST["medico"];

	$statement=mysqli_prepare($con,"INSERT INTO users(email,clave,nombre,edad,telefono,direccion,provincia,medico)VALUES(?,?,?,?,?,?,?,?)");
	mysqli_stmt_bind_param($statement,"ssis",$email,$clave,$nombre,$edad,$telefono,$direccion,$provincia,$medico);
	mysqli_stmt_execute($statement);
	$response=array();
	$response["success"]=true;
	echo json_encode($response);

?>
