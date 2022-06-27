<?php


require_once './connect.php';


if($_POST){
    $api = array();

    $email = $_POST['email'];

$sql ="SELECT * FROM `customer_orders` WHERE customer_email = '$email'";

if($result = mysqli_query($link,$sql)){
    

    while($row = $result->fetch_assoc()){

        $api[] = $row;
    }

    echo stripslashes(json_encode($api,JSON_UNESCAPED_SLASHES));
}




}


?>