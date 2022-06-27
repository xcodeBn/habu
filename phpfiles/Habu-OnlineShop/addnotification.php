<?php

require_once './connect.php';

$token = rand(100000,10000000);

if($_POST){



    $title = $_POST['title'];
    $body = $_POST['body'];

    $sql = "INSERT INTO `notification`(`token`, `title`, `text`) VALUES ('$token','$title','$body')";

    if($result = mysqli_query($link,$sql)){


        echo "Success";
    }
    
    

}




?>