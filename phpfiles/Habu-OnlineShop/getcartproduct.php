<?php
require_once './connect.php';


if($_POST){

$api = array();

$pid = $_POST['id'];

$sql = "SELECT * FROM `product` WHERE id = '$pid'";

if($result = mysqli_query($link,$sql)){

    while($row= $result->fetch_assoc()){

        $api[] = $row;

    }

    
echo json_encode($api);
}



}



?>