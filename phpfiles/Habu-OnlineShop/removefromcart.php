<?php

require_once './connect.php';
$apiarray = array();
if($_POST){

    $pid = $_POST['pid'];
    $email = $_POST['email'];

    $sql = "DELETE FROM `cart` WHERE email = '$email' AND product = '$pid'";


    if($result = mysqli_query($link,$sql)){

        $apiarray['error'] = false;
        $apiarray['error_message']= '';
    }
    else{
        $apiarray['error'] = true;
        $apiarray['error_message']= mysqli_error($link);
    }

    echo json_encode($apiarray);

}





?>