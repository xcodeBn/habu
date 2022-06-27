<?php

require_once './connect.php';

if($_POST){

$id = $_POST['id'];


$securitykey = "aicnaicbiecnaon12421312312xsadmaoiwjidoaj141242oakmsclncejoS1212311dijaoWJIdjSDJWIDJSKC1231231OEJAIDNIADNSJNCJENCE";
$sql = "DELETE FROM `category` WHERE id ='$id'";
$key = $_POST['key'];
$api = array();


if($key != $securitykey){

$api['error']=true;
$api['error_message'] = 'access_denied';



}

else{


    if($do = mysqli_query($link,$sql)){

        $api['error']=false;
        $api['error_message'] = '';

    }

    else{
        $api['error']=true;
$api['error_message'] = mysqli_error($link);

    }
}


echo json_encode($api);



}







?>