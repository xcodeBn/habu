<?php
include_once './connect.php';
include_once './sendemail.php';
if($_GET){


$email = $_GET['mail'];

$q = "SELECT * FROM `customer_info` WHERE email = '$email'";

$do = mysqli_query($link,$q);

if($do->num_rows ==0){
        $errarr['error']=true ;
        $errarr['error_message']= 'This email address does not exist !';
        echo json_encode($errarr);
}

else {

        $row = $do ->fetch_assoc();
        $token = $row['token'];
        $name = $row['name'];

       
        $errarr['token'] = $token;
        echo json_encode($errarr);


        sendemailmessage($email,$name,$token);
        



}}

?>