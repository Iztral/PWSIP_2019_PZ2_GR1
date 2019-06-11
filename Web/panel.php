<?php 
ob_start();
session_start();
require_once 'config.php';

$tab_selection = 1;

if(!isset($_SESSION['user'])){
	header("Location: index.php");
}
if(isset($_SESSION['user'])){
	$id = $_SESSION['user'];
}

//get loginInfo
$query_user = "SELECT Login, Email FROM Users WHERE ID='$id'";
$res_user=mysqli_query($conn, $query_user);
$row_user=mysqli_fetch_array($res_user);
$login = $row_user['Login'];

//get OrderData
$order_array = array();
$query_orders = "SELECT ID, Description, State, CreateDate, ModificationDate FROM Orders WHERE UserID='$id' ORDER BY ID ASC;";
$res_orders=mysqli_query($conn, $query_orders);
echo(mysqli_error($conn));
while ($row = $res_orders->fetch_assoc())
{
	$order_array[] = $row;
}

//get UserData
$query_data = "SELECT Name, Surname, Address, Telephone, City FROM UserData WHERE UserID='$id'";
$res_data=mysqli_query($conn, $query_data);
$row_data=mysqli_fetch_array($res_data);
echo(mysqli_error($conn));

//update UserData
function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

$nameErr = $surnameErr = $phoneErr = $addressErr = $cityErr = "";
$name = $surname= $phone = $address = $city = "";

$error = false;

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['name'])) {
	if (empty($_POST["name"])) {
    $nameErr = "Can't be empty";
	$error = true;
  } else {
    $name = test_input($_POST["name"]);
  }
	if (empty($_POST["surname"])) {
    $surnameErr = "Can't be empty";
	$error = true;
  } else {
    $surname = test_input($_POST["surname"]);
  }
	if (empty($_POST["phone"])) {
    $phoneErr = "Can't be empty";
	$error = true;	
  } else {
    $phone = test_input($_POST["phone"]);
  }
	if (empty($_POST["address"])) {
    $addressErr = "Can't be empty";
	$error = true;
  } else {
    $address = test_input($_POST["address"]);
  }
	if (empty($_POST["city"])) {
    $cityErr = "Can't be empty";
	$error = true;
  } else {
    $city = test_input($_POST["city"]);
  }
	if($error!=true){
		$query_update = "UPDATE UserData SET Name = '$name', Surname = '$surname', Address = '$address', Telephone = '$phone', City = '$city' WHERE UserID = '$id'";
		$res_update = mysqli_query($conn, $query_update);
		echo(mysqli_error($conn));

		if ($res_update) {
			$errTyp = "success";
			$errMSG = "Data successfully edited.";
			header("location: panel.php");
		} else {
			$errTyp = "danger";
			$errMSG = "Something went wrong, try again later...";
			echo($conn->error);
		}
	}
	
}

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['result']) && isset($_POST['order_id'])){
	$state = $_POST["result"];
	$order_id = $_POST["order_id"];
	$query_update = "UPDATE Orders SET State = '$state', ModificationDate = CURDATE() WHERE ID = '$order_id'";
		$res_update = mysqli_query($conn, $query_update);
		echo(mysqli_error($conn));

		if ($res_update) {
			$errTyp = "success";
			$errMSG = "Data successfully edited.";
			header("location: panel.php");
		} else {
			$errTyp = "danger";
			$errMSG = "Something went wrong, try again later...";
			echo($conn->error);
		}
	
}
?>
<!DOCTYPE html>
<html>
<head>
	<title>Panel Użytkownika</title>
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

<div class="card mx-auto" style="width: 70%; height: 70%; margin-top: 3%; margin-bottom: 3%">
	<div class="card-header">
		<h1 style="margin-left: 10px;">Witaj, <?php echo($login); ?>!</h1>
	</div>
	<div class="card-body">
		<ul class="nav nav-tabs">
			<li class="nav-item">
				<a class="nav-link active" data-toggle="tab" href="#home">Aktywne Zlecenia</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" data-toggle="tab" href="#menu1">Archiwum Zleceń</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" data-toggle="tab" href="#menu2">Dane Osobowe</a>
			</li>
		</ul>

		<div class="tab-content">
			<div class="tab-pane container active" style="padding-top: 8px;" id="home">
				<?php if(!empty($order_array)){ ?>
					<?php foreach($order_array as $id => $order){?>
						<?php if ($order["State"] != 'success' && $order["State"] != 'failed') {?>
						<div class="card">
							<div class="card-header">
								<?php $id++;?>
								<?php echo('Aktywne zlecenie #'.$id)?>
							</div>
							<div class="card-body">
								<h3>Status: <?php echo($order["State"])?></h3>
								<br>Opis: <?php echo($order["Description"])?>
								<br>Data złożenia: <?php echo($order["CreateDate"])?>
								<br>Data modyfikacji: <?php echo($order["ModificationDate"])?>
								<br>Zakończ zlecenie:
								<form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
									<select name="result">
										<option value="failed">Niedostarczono</option>
										<option value="success">Dostarczono</option>
									</select>
									<input name="order_id" type="number" readonly value="<?php echo($order["ID"])?>" style="display: none">
									<input type="submit" value="Zakończ">
								</form>
							</div>
						</div>
					<?php }?>
				<?php }?>
			<?php }?>
			</div>
			
			<div class="tab-pane container fade" style="padding-top: 8px;" id="menu1">
				<?php if(!empty($order_array)){ ?>
					<?php foreach($order_array as $id => $order){?>
						<?php if ($order["State"] == 'success' || $order["State"] == 'failed') {?>
						<div class="card">
						<?php if ($order["State"] == 'success'){
							echo('<div class="card-header bg-success">');}
							if ($order["State"] == 'failed'){
							echo('<div class="card-header bg-danger">');}?>
								<?php $id++;?>
								<?php echo('Archiwizowane zlecenie #'.$id)?>
							</div>
							<div class="card-body">
								<h3>Status: <?php echo($order["State"])?></h3>
								<br>Opis: <?php echo($order["Description"])?>
								<br>Data złożenia: <?php echo($order["CreateDate"])?>
								<br>Data modyfikacji: <?php echo($order["ModificationDate"])?>
							</div>
						</div>
						<br>
					<?php }?>
				<?php }?>
			<?php }?>
			</div>
			
			<div class="tab-pane container fade" style="padding-top: 8px;" id="menu2">
				<form class="form-horizontal" method="post" action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" autocomplete="off" style="width: 90%">
					<div class="form-group">
						<label class="control-label col-sm-2" for="email">Imię:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="name" required value="<?php echo ($row_data['Name']); ?>">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="email">Nazwisko:</label>
						<div class="col-sm-10">
							<input type="text" name="surname" class="form-control" required value="<?php echo ($row_data['Surname']); ?>">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="email">Telefon:</label>
						<div class="col-sm-10">
							<input type="number" pattern="[0-9]" name="phone" maxlength="12" onKeyPress="isNumberKey(event)" class="form-control" required value="<?php echo ($row_data['Telephone']); ?>">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="email">Adres:</label>
						<div class="col-sm-10">
							<input type="text" name="address" class="form-control" required value="<?php echo ($row_data['Address']); ?>">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="email">Miasto:</label>
						<div class="col-sm-10">
							<input type="text" name="city" class="form-control" required value="<?php echo ($row_data['City']); ?>">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" value="Aktualizuj">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>




</body>
<script>
	function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}
	</script>
</html>