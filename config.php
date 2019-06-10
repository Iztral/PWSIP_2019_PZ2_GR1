<?php
error_reporting( ~E_DEPRECATED & ~E_NOTICE );

define("HOST", "mn14.webd.pl");
define("USER", "teleman1_new");
define("PASS", "haslo1234");
define("DATABASE", "teleman1_teleman");

$conn = mysqli_connect(HOST, USER, PASS, DATABASE);

if ( !$conn ) {
	die("Connection failed : " . mysql_error());
}

?>