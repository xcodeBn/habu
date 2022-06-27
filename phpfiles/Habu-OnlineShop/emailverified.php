<?php

require_once './connect.php';
if($_POST){

    $email = $_POST['email'];

    $sql  = "UPDATE `customer_info` SET `isverified`='1' WHERE email = '$email'";

    $api = array();
    if(mysqli_query($link,$sql)){
        $api['error'] = false;
        $api['error_message'] = "";
    }
    else{
        $api['error'] = true;
        $api['error_message'] =mysqli_error($link);
    }

    echo json_encode($api);

}




?>