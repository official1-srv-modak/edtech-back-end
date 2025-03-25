<?php
include("cors_policy.php");
//include("reload_disk.php");
include("path.php"); 
include("db.php");

// Get the limit from the URL parameters, default to 5 if not provided or invalid
$limit = isset($_GET['limit']) && is_numeric($_GET['limit']) ? intval($_GET['limit']) : 5;

// SQL query to select distinct random movies from the Movies category with the specified limit
$sql = "SELECT DISTINCT *
        FROM modak_flix.file_system 
        WHERE category='Movies' 
        ORDER BY RAND() 
        LIMIT $limit";

$result = $con->query($sql);
$rows = array();

if ($result->num_rows > 0) {
    // Output data of each row
    while ($row = $result->fetch_assoc()) {
        $rows[] = $row;
    }
} else {
    // Optionally handle the case with no results
    // echo "0 results from Movies</br>";
}

echo '{"cards":' . json_encode($rows) . "}";
?>
