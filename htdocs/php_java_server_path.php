<?php
include("path.php");
include("cors_policy.php");
error_reporting(0);
$arr = get_defined_vars();
print_r($arr["path_server"]);
?>