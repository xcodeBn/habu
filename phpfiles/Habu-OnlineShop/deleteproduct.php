<?php
require_once './connect.php';

if($_POST){

    $id = $_POST['id'];
$sql = "DELETE FROM `product` WHERE id = '$id'";
$api = array();
if($result = mysqli_query($link,$sql)){


    $api['error'] = false;
    
}

else{

    $api['error']=true;
}

echo json_encode($api);

}








?>