<?php
include("reload_disk.php");
include("cors_policy.php");
include("path.php"); 
include("db.php");

// SQL query to select movies with an IMDb rating above 7.5
$sql = "SELECT DISTINCT name, des, category, path, language, album_art_path, url FROM modak_flix.file_system WHERE category='Movies' AND des LIKE '%IMDB Rating :%' ORDER BY NAME";
$result = $con->query($sql);
$rows = array();

if ($result->num_rows > 0) {
    // output data of each row
    while ($row = $result->fetch_assoc()) {
        // Extract IMDb rating from the description
        preg_match('/IMDB Rating : ([\d\.]+)/', $row['des'], $matches);
        
        // Check if IMDb rating was found and is above 7.5
        if (isset($matches[1]) && floatval($matches[1]) > 7.5) {
            $row['imdb_rating'] = $matches[1]; // Add IMDb rating to the row
            $rows[] = $row; // Append to results
        }
    }
} else {
    //echo "0 results from Movies</br>";
}

echo '{"cards":' . json_encode($rows) . "}";
?>