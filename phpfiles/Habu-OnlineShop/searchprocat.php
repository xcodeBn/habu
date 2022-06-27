<?php
require_once './connect.php';

if($_GET){


    $name = $_GET['name'];
    $sql = "SELECT * FROM `product` WHERE name LIKE '%$name%'";

    if($result = mysqli_query($link,$sql)){

        while($row = $result->fetch_assoc()){

            $api = array();
            $api[]=$row;
        }

        echo json_encode($api);

    }
}



?>