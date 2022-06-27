<?php

require_once './connect.php';


$sql = "SELECT * FROM `product`";


if($result = mysqli_query($link,$sql)){

$api = array();

while($row = $result->fetch_assoc()){

    $api[] = $row;
}

json_encode($api);

}




?>