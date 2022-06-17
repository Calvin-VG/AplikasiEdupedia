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
		$id_profile = $_POST['id_profile'];
		$full_name = $_POST['full_name'];
		$username = $_POST['username'];
		$email = $_POST['email'];
		$phone_number = $_POST['phone_number'];
		$password = $_POST['password'];
		$image = $_POST['fileUpload'];

		// list($type, $image) = explode(';', $image);
		// list(, $image)      = explode(',', $image);

		// echo $image;

		$image = base64_decode($image);

		file_put_contents('images/'.$username.'.jpg', $image);
		$imagename = $username.'.jpg';

		// insert data
		$sql1 = "UPDATE profile SET
					full_name = '$full_name',
					username = '$username', 
					email = '$email',
					phone_number = '$phone_number',
					password = '$password', 
					image = '$imagename' 
				WHERE id_profile = '$id_profile'";
		$conn->query($sql1);

		$sql2 = "UPDATE forgot_password SET
					email = '$email',
					password = '$password'
				WHERE id_forgot_password = '$id_profile'";
		$conn->query($sql2);

		$sql3 = "UPDATE login SET
					username = '$username',
					password = '$password'
				WHERE id_login = '$id_profile'";
		$conn->query($sql3);

		$result['status']['code'] = 200;
		$result['status']['description'] = "1 data inserted";
		$result['result'] = array(
			"full_name"=>$full_name,
			"username"=>$username,
			"email"=>$email,
			"phone_number"=>$phone_number,
			"password"=>$password,
            "image"=>$imagename
		);

	}else{
		$result['status']['code'] = 400;
	}

	// parse ke format json
	echo json_encode($result);
?>