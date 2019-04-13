<?php
error_reporting( ~E_DEPRECATED & ~E_NOTICE );

define("HOST", "localhost");
define("USER", "");
define("PASSWORD", "");
define("DATABASE", "");

$conn = mysqli_connect(HOST,USER,PASSWORD, DATABASE);

if ( !$conn ) {
	die("Connection failed : " . mysql_error());
}

?>