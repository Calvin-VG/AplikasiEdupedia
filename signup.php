<?php
	// header harus json
	header("Content-Type:application/json");

	// conf koneksi db
	$servername = "localhost";
    $username = "root";
    $password = "rahasia";
    $dbnamea = "edupedia";

    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbnamea);

	// tangkap method request
	$smethod = $_SERVER['REQUEST_METHOD'];

	// inisialisasi variable hasil
	$result = array();

	// kondisi metode
	if($smethod == 'POST'){
		// tangkap input
		$full_name = $_POST['full_name'];
		$username = $_POST['username'];
		$email = $_POST['email'];
		$phone_number = $_POST['phone_number'];
		$password = $_POST['password'];

		// insert data
		$sql1 = "INSERT INTO login (
					username,  
					password) 
				VALUES (
					'$username', 
					'$password')";
		$conn->query($sql1);

		// insert data
		$sql2 = "INSERT INTO forgot_password (
					email,  
					password) 
				VALUES (
					'$email', 
					'$password')";
		$conn->query($sql2);

		// insert data
		$sql3 = "INSERT INTO profile (
					full_name,
					username, 
					email,
					phone_number,
					password) 
				VALUES (
					'$full_name',
					'$username', 
					'$email',
					'$phone_number', 
					'$password')";
		$conn->query($sql3);

		$result['status']['code'] = 200;
		$result['status']['description'] = "1 data inserted";
		$result['result'] = array(
			"full_name"=>$full_name,
			"username"=>$username,
			"email"=>$email,
			"phone_number"=>$phone_number,
			"password"=>$password
		);

	}else{
		$result['status']['code'] = 400;
	}

	// parse ke format json
	echo json_encode($result);
?>