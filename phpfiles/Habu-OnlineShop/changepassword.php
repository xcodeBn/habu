<?php

require_once './connect.php';
$apiarray = array();

if($_POST){


$email = addslashes(strip_tags($_POST['email']));
$password = addslashes(strip_tags($_POST['password']));
$key = addslashes(strip_tags($_POST['key']));



$securitykey = "aicnaicbiecnaon12421312312xsadmaoiwjidoaj141242oakmsclncejoS1212311dijaoWJIdjSDJWIDJSKC1231231OEJAIDNIADNSJNCJENCE";


if($key!=$securitykey){

    $apiarray['error']=true;
    $apiarray['error_message']='Access Denied';
}

else{

    $updateq="UPDATE `customer_info` SET `password`='$password' WHERE email = '$email'";

    if($result=mysqli_query($link,$updateq)){

        $apiarray['error'] = false;
        $apiarray['error_message'] = '';


    }


    else{

        $apiarray['error'] = true;
        $apiarray['error_message'] = mysqli_error($link);
    }


}









}

else{



    $apiarray['error']=true;
    $apiarray['error_message']='No Values';

}


echo json_encode($apiarray);





?>