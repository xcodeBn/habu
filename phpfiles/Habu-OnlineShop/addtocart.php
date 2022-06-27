<?php

require_once './connect.php';

$apiarray = array();
if($_POST){

    $pid =$_POST['pid'];
    $email = $_POST['email'];


$sql = "INSERT INTO `cart`( `email`, `product`) VALUES ('$email','$pid')";

$q = "SELECT * FROM `cart` WHERE product='$pid' AND email = '$email'";


if($res = mysqli_query($link,$q)){

    if($res->num_rows >0){
        $apiarray['error'] = true;
        $apiarray['error_message'] = 'Product already in your cart!';
    }

    else{
        if($result=mysqli_query($link,$sql)){

            $apiarray['error'] = false;
        $apiarray['error_message'] = '';
        }

        else{
            $apiarray['error'] = true;
            $apiarray['error_message'] = mysqli_error($link);
        }
    }


}

else{
    $apiarray['error'] = true;
    $apiarray['error_message'] = mysqli_error($link);
}

echo json_encode($apiarray);
}

?>