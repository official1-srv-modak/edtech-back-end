<?php
include("reload_disk.php");
include("path.php");
include("db.php");
include("cors_policy.php");

// SQL query to select one random movie from the file_system where category is 'Movies'
$sql = "SELECT * FROM modak_flix.file_system WHERE category='Movies' ORDER BY RAND() LIMIT 1";
$result = $con->query($sql);
$rows = array();

if ($result->num_rows > 0) {
    // output data of the random row
    $row = $result->fetch_assoc();
    $rows[] = $row;  // Add the single random row to the array
} else {
    // Optional: Handle the case when no results are found
    // echo "No results found for Movies category.</br>";
}

// Send the response as a JSON object
echo json_encode(array("card" => $rows));
?>
