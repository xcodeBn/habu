<?php
include ('./connect.php');

$apiarray = array();
$securitykey = "aicnaicbiecnaon12421312312xsadmaoiwjidoaj141242oakmsclncejoS1212311dijaoWJIdjSDJWIDJSKC1231231OEJAIDNIADNSJNCJENCE";

function signup($useremail,$userpassword,$username,$userlocation,$token,$date){
//use this function to sign up to the database

global $link;
global $apiarray;


$signupquery = "INSERT INTO `customer_info`(`email`, `password`, `name`, `location`, `token`, `isverified`,`CreationDate`) VALUES ('$useremail','$userpassword','$username','$userlocation','$token','0','$date')";
$submit = mysqli_query($link,$signupquery);


if($submit){
$apiarray['error'] = false;
$apiarray['error_message'] = 'no erros detected,sign up completed succefully';
}
else{
     $apiarray['error'] = true;
     $apiarray['error_message'] = mysqli_error($link);
    }


   

}








?>