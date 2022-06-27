<?php
require_once './connect.php';


if($_POST){
    $api = array();
$id = $_POST['id'];

$sql = "DELETE FROM `report` WHERE id = '$id'";

if($result = mysqli_query($link,$sql)){


   
    $api['error']=false;

}

else {
    $api['error']=true;
}


echo json_encode($api);

}



?>