<?php

$db_host = "d5x4ae6ze2og6sjo.cbetxkdyhwsb.us-east-1.rds.amazonaws.com";
$db_name = "euwd9h6p5k605t8z";
$db_user = "cc6hx3z3huxqsogi";
$db_password = "u99oplxd3ojzupx0";

$connection = mysql_connect($db_host, $db_user, $db_password) or die("Connection Error: " . mysql_error());

mysql_select_db($db_name) or die("Error al seleccionar la base de datos:".mysql_error());
    @mysql_query("SET NAMES 'utf8'");

$sql_query = "SELECT * FROM clientes;";
$result = mysql_query($sql_query);
$rows = array();
while($r = mysql_fetch_assoc($result)) {
  $rows[] = $r;
}
print json_encode($rows);

?>