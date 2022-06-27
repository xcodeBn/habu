<?php
require_once './connect.php';


if($_POST){

    $id = $_POST['id'];
    $size = $_POST['size'];

    $api = array();

    $sql = "UPDATE `product` SET `size`='$size' WHERE id = '$id'";

    if($do = mysqli_query($link,$sql)){

        $api['error']=false;


    }

    else{

        $api['error']=true;
    }


    echo json_encode($api);

}





?>