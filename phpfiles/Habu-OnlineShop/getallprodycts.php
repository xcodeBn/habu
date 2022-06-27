<?php

require_once './connect.php';


$sql = "SELECT * FROM `product`";

$api = array();
if($result = mysqli_query($link,$sql)){

    while($row = $result->fetch_assoc()){

        $api[] = $row;
    }

   echo json_encode($api);
}


?>