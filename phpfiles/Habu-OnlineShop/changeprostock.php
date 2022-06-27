<?php
require_once './connect.php';


if($_POST){

    $id = $_POST['id'];
    $stock = $_POST['stock'];

    $api = array();

    $sql = "UPDATE `product` SET `stock`='$stock' WHERE id = '$id'";

    if($do = mysqli_query($link,$sql)){

        $api['error']=false;


    }

    else{

        $api['error']=true;
    }


    echo json_encode($api);

}





?>