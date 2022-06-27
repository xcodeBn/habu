<?php

require_once './connect.php';
$apiarray= array();


if($_POST){


    $pid = $_POST['id'];
    $email = $_POST['email'];
$sql = "SELECT `id`, `email`, `product` FROM `cart` WHERE product='$pid' AND email='$email'";

if($result = mysqli_query($link,$sql)){

if($result ->num_rows >0){

    $apiarray['error'] = false;
    $apiarray['error_message'] = '';
    $apiarray['in'] = true;
}

else{

    $apiarray['error'] = false;
    $apiarray['error_message'] = mysqli_error($link);
    $apiarray['in'] = false;
    
}




}

else {

    $apiarray['error'] = true;
    $apiarray['error_message'] = mysqli_error($link);
}


}

else{
    $apiarray['error'] = true;
    $apiarray['error_message'] = 'No Values Entered';
    $apiarray['in'] = '';
}

echo json_encode($apiarray);


?>