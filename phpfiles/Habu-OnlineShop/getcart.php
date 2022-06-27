<?php
require_once './connect.php';

if($_POST){

$api = array();

$email= $_POST['email'];


$sql = "SELECT * FROM `cart` WHERE email ='$email'";

if($result = mysqli_query($link,$sql)){

while($row = $result->fetch_assoc()){

    $api[]=$row;
}


echo json_encode($api);
}


}







?>