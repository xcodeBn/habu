<?php
require_once './connect.php';

$apiarray=array();


if($_POST){


    $pid = $_POST['pid'];
    $email = $_POST['email'];
    $title = $_POST['title'];
    $rate = $_POST['rate'];
    $text = $_POST['text'];

    $sql = "INSERT INTO `review`( `productid`, `email`, `title`, `rate`, `text`) VALUES ('$pid','$email','$title','$rate','$text')";


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