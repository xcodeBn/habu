<?php
require_once './connect.php';



$sql = "SELECT email,name FROM `customer_info` WHERE 1";


$api = array();

if($res = mysqli_query($link,$sql)){


    while($row = $res->fetch_assoc()){
        $api[] = $row;
    }

    echo json_encode($api);
    
}

?>