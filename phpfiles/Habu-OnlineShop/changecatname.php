<?php
require_once './connect.php';


if($_POST){

    $id = $_POST['id'];
    $name = $_POST['name'];

    $api = array();

    $sql = "UPDATE `category` SET `name`='$name' WHERE id = '$id'";

    if($do = mysqli_query($link,$sql)){

        $api['error']=false;


    }

    else{

        $api['error']=true;
    }


    echo json_encode($api);

}





?>