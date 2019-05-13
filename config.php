<?php
error_reporting( ~E_DEPRECATED & ~E_NOTICE );

define("HOST", "mysql.cba.pl");
define("USER", "teleman");
define("PASS", "Hasloteleman1");
define("DATABASE", "teleman");

$conn = mysqli_connect(HOST, USER, PASS, DATABASE);

if ( !$conn ) {
	die("Connection failed : " . mysql_error());
}

?>