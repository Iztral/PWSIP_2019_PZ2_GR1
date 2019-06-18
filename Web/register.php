<?php
ob_start();
session_start();
if( isset($_SESSION['user'])!="" ){
	header("Location: index.php");
}
include_once 'config.php';

$email = $pass = $login = '';
$emailError = $passError = $loginError = '';

$error = false;

if ( isset($_POST['btn-signup']) ) {
	$login = trim($_POST['login']);
	$login = strip_tags($login);
	$login = htmlspecialchars($login);

	$email = trim($_POST['email']);
	$email = strip_tags($email);
	$email = htmlspecialchars($email);

	$pass = trim($_POST['pass']);
	$pass = strip_tags($pass);
	$pass = htmlspecialchars($pass);

	if (empty($login)) {
		$error = true;
		$loginError = "Proszę wprowadź login.";
	} else if (strlen($login) < 6) {
		$error = true;
		$loginError = "Przynajmniej 6 znaków.";
	} else if (!preg_match("/^[a-zA-Z0-9]+$/",$login)) {
		$error = true;
		$loginError = "Musi zawierać litery i cyfry.";
	} else {
		$query = "SELECT Login FROM Users WHERE Login='$login'";
		$result = mysqli_query($conn, $query);
		$count = mysqli_num_rows($result);
		if($count!=0){
			$error = true;
			$emailError = "Login jest już użyty.";
		}
	}
	
	if ( !filter_var($email,FILTER_VALIDATE_EMAIL) ) {
		$error = true;
		$emailError = "Wprowadź poprawny email.";
	} else {
		$query = "SELECT Email FROM Users WHERE Email='$email'";
		$result = mysqli_query($conn, $query);
		$count = mysqli_num_rows($result);
		if($count!=0){
			$error = true;
			$emailError = "Email jest już użyty.";
		}
	}

	if (empty($pass)){
		$error = true;
		$passError = "Proszę wprowadź hasło.";
	} else if(strlen($pass) < 6) {
		$error = true;
		$passError = "Przynajmniej 6 znaków.";
	}

	$password = $pass;

	if( !$error ) {
        $password = hash('sha256',$password);
		$query = "INSERT INTO Users(Login, Email, Password, Rank) VALUES('$login','$email','$password', 'user')";
		$res = mysqli_query($conn, $query);

		if ($res) {
			$errTyp = "success";
			$errMSG = "Pomyślna rejestracja, możesz teraz się zalogować.";
			
			$query_data = "SELECT ID FROM Users WHERE Login = '$login'";
			$res_data = mysqli_query($conn, $query_data);
			$row_data=mysqli_fetch_array($res_data);
			$userID = $row_data['ID'];
			
			$query_insert = "INSERT INTO UserData(UserID) VALUES('$userID')";
			$res_insert = mysqli_query($conn, $query_insert);
			unset($login);
			unset($email);
			unset($pass);
		} else {
			$errTyp = "danger";
			$errMSG = "Wystąpił błąd..."; 
		}
		
		

	}

}
?>


<!DOCTYPE html>
<html lang="en">
<head>
<title>Rejestracja</title>
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
	<div class="container">
		<div id="login-form">
			<form method="post" action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" autocomplete="off">
				<div class="col-md-12">
					<div class="form-group">
						<h2 class="">Rejestracja</h2>
					</div>
					<div class="form-group">
						<hr/>
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
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
							<input type="text" name="login" class="form-control" placeholder="Twój login" maxlength="50" autocomplete="off" value="<?php echo $login ?>" />
						</div>
						<span class="text-danger"><?php echo $loginError; ?></span>
					</div>

					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
							<input type="email" name="email" class="form-control" placeholder="Twój Email" autocomplete="off" maxlength="40" value="<?php echo $email ?>" />
						</div>
						<span class="text-danger"><?php echo $emailError; ?></span>
					</div>

					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
							<input type="password" name="pass" class="form-control" autocomplete="off" placeholder="Twoje hasło" maxlength="15" />
						</div>
						<span class="text-danger"><?php echo $passError; ?></span>
					</div>

					<div class="form-group">
						<hr />
					</div>

					<div class="form-group">
						<button type="submit" class="btn btn-block btn-primary" name="btn-signup">Rejestracja</button>
					</div>

					<div class="form-group">
						<hr />
					</div>

					<div class="form-group">
						<a href="login.php">Logowanie</a>
					</div>
				</div>
			</form>
		</div> 
	</div>
</body>
</html>
<?php ob_end_flush(); ?>