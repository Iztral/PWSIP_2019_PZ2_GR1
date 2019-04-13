<?php 
ob_start();
session_start();
require_once 'config.php';

if(!isset($_SESSION['user'])){
	header("Location: index.php");
}

if(isset($_SESSION['user'])){
	$id = $_SESSION['user'];
}

$query_data = "SELECT Name, Surname, Address, Telephone, City FROM UserData WHERE UserID='$id'";
$res_data=mysqli_query($conn, $query_data);
$row_data=mysqli_fetch_array($res_data);
echo(mysqli_error($conn));

//get User
$query_mail = "SELECT Email FROM Users WHERE ID='$id'";
$res_mail=mysqli_query($conn, $query_mail);
$row_mail=mysqli_fetch_array($res_mail);
echo(mysqli_error($conn));

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

$nameErr = $surnameErr = $telephoneErr = $addressErr = $cityErr = $descriptionErr = "";
$name = $surname  = $telephone = $address = $city = $description = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
	
	if (empty($_POST["phone"])) {
    $error = true;
  } else {
    $phone = test_input($_POST["phone"]);
  }
	if (empty($_POST["address"])) {
    $error = true;
  } else {
    $address = test_input($_POST["address"]);
  }
	if (empty($_POST["city"])) {
    $error = true;
  } else {
    $city = test_input($_POST["city"]);
  }
	if (empty($_POST["description"])) {
    $error = true;
  } else {
    $description = test_input($_POST["description"]);
  }
	if($error!=true){
		$query_insert = "INSERT INTO Orders(UserID, Address, Telephone, City, Description, State, CreateDate) VALUES($id, '$address', '$phone', '$city', '$description', 'waiting', CURDATE())";
		$res_insert = mysqli_query($conn, $query_insert);
		echo(mysqli_error($conn));
		if ($res_insert) {
			$errTyp = "success";
			$errMSG = "Zlecenie pomyślnie złożone.";
			
			$to      = $row_mail["Email"];
			$subject = 'Zlecenie';
			$message = 'Zlecenie zostało złożone' . "\r\n" .
				'Dane zlecenia: ' . "\r\n" .
				'	Adres: '.$address."\r\n".
				'	Miasto: '.$city."\r\n".
				'	Telefon:'.$phone."\r\n".
				'	Opis: '.$description."\r\n";
			$headers = 'Od: admin@mfixit.cba.pl' . "\r\n" .
				'Odpowiedz: admin@mfixit.cba.pl' . "\r\n" .
				'X-Mailer: PHP/' . phpversion();

			mail($to, $subject, $message, $headers);
		}
		else{
			$errTyp = "danger";
			$errMSG = "Wystąpił błąd...";
		}
	}
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Form</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
  <link rel="stylesheet" href="style.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
	<link rel="icon" type="image/png" href="img/favico.png">
</head>
<body>
    
    <?php if(isset($_SESSION['user'])){
		readfile("nav_logged.html");
	}elseif(!isset($_SESSION['user'])){
		readfile("nav_not_logged.html");
	}
	?>
    </ul>
  </div>
	</nav>
	<div class="card mx-auto" style="width: 70%; height: 70%; margin-top: 3%; margin-bottom: 3%">
		<div class="card-header">
			<h2 style="margin-left: 10px;">Wypełnij formularz aby złożyć zlecenie:</h2>
		</div>
		<div class="card-body">
			<form id="usrform" class="form-horizontal" method="post" action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" autocomplete="off" >
					<div class="form-group">
						<label class="control-label col-sm-2">Telefon:</label>
						<div class="col-sm-10">
						  <input type="number" pattern="[0-9]" name="phone" maxlength="12" onKeyPress="isNumberKey(event)" class="form-control" required value="<?php echo ($row_data['Telephone']); ?>">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2">Adres:</label>
						<div class="col-sm-10">
						  <input type="text" name="address" class="form-control" required value="<?php echo ($row_data['Address']); ?>">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2">Miasto:</label>
						<div class="col-sm-10">
						  <input type="text" name="city" class="form-control" required value="<?php echo ($row_data['City']); ?>">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" >Opis Dostawy:</label>
						<div class="col-sm-10">
							<textarea name="description" form="usrform" class="form-control" required> </textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
						  <input type="submit" value="Złóż Zlecenie">
						</div>
					</div>
					<?php
						if (isset($errMSG)){
					?>
							<div class="form-group">
							<div class="alert alert-<?php echo ($errTyp=="success") ? "success" : $errTyp; ?>">
							<span class="glyphicon glyphicon-info-sign"></span> <?php echo $errMSG; ?>
							</div>
							</div>
					<?php
						}
					?>
			</form>
		</div>
	</div>
</body>
</html>