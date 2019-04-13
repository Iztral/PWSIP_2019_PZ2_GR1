<?php
ob_start();
session_start();
require_once 'config.php';

$email = $pass = '';
$emailError = $passError = '';
	
if (isset($_SESSION['user'])!="" ) {
	header("Location: index.php");
exit;
}

$error = false;

if( isset($_POST['btn-login']) ) {

	$email = trim($_POST['email']);
	$email = strip_tags($email);
	$email = htmlspecialchars($email);

	$pass = trim($_POST['pass']);
	$pass = strip_tags($pass);
	$pass = htmlspecialchars($pass);

	if(empty($email)){
		$error = true;
		$emailError = "Please enter your email address.";
	} else if ( !filter_var($email,FILTER_VALIDATE_EMAIL) ) {
		$error = true;
		$emailError = "Please enter valid email address.";
	}

	if(empty($pass)){
		$error = true;
		$passError = "Please enter your password.";
	}

	if (!$error) {
		//$password = hash('sha256', $pass);
		$password = $pass;
		$query = "SELECT ID, Login, Password, Rank FROM Users WHERE Email='$email'";
		$res=mysqli_query($conn, $query);
		$row=mysqli_fetch_array($res);
		$count = mysqli_num_rows($res);
		if( $count == 1 && $row['Password']==$password && $row['Rank'] == "user") {
			$_SESSION['user'] = $row['ID'];
			header("Location: index.php");
		} else {
			$errMSG = "Invalid login data.";
		}
	}
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Login</title>
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
<div class="container">
	<div id="login-form">
		<form method="post" action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" autocomplete="off">
			<div class="col-md-12">
				<div class="form-group">
					<h2 class="">Logowanie</h2>
				</div>
				<div class="form-group">
					<hr />
				</div>
				<?php
					if ( isset($errMSG) ) {
				?>
						<div class="form-group">
							<div class="alert alert-danger">
								<span class="glyphicon glyphicon-info-sign"></span> <?php echo $errMSG; ?>
							</div>
						</div>
				<?php
										}
				?>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
						<input type="email" name="email" class="form-control" value="<?php echo $email; ?>" maxlength="40" />
					</div>
					<span class="text-danger"><?php echo $emailError; ?></span>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
						<input type="password" name="pass" class="form-control"  maxlength="15" />
					</div>
					<span class="text-danger"><?php echo $passError; ?></span>
				</div>
				<div class="form-group">
					<hr />
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-block btn-primary" name="btn-login">Zaloguj</button>
				</div>
				<div class="form-group">
					<hr />
				</div>
				<div class="form-group">
					<a href="register.php">Rejestracja</a>
				</div>
			</div>
		</form>
	</div> 
</div>
</body>
</html>