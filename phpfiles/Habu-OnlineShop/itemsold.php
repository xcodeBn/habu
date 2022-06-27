<?php

require_once './connect.php';

$sql="SELECT COUNT(id) FROM customer_orders WHERE 1";


if($result = mysqli_query($link,$sql)){

    $arr = array();
    while($row = $result->fetch_assoc()){

        $arr[] = $row;
    }

    echo json_encode($arr);
}

?>