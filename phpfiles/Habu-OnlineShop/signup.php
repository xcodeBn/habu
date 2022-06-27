<?php 
require './connect.php';
require './sendemail.php';
require './functions.php';
$email = addslashes(strip_tags($_POST['email']));
$name = addslashes(strip_tags($_POST['name']));
$password = addslashes(strip_tags($_POST['password']));
$location = addslashes(strip_tags($_POST['location']));
$date = addslashes(strip_tags($_POST['date']));
$key = addslashes(strip_tags($_POST['key']));
$token = rand(10000,99999);


$key = addslashes(strip_tags($_POST['key']));
$CheckemailString = "SELECT * FROM `customer_info` WHERE email = '$email'";;



if($key!=$securitykey)
{

$apiarray['error']=true;
$apiarray['error_message']='Access Denied';

echo json_encode($apiarray);
die();

}




if (trim($email) == "" or trim($password) == "" or  trim($name) == "" or  trim($location) == "" or  trim($token) == ""    )
    {

        $apiarray['error']=true;
        $apiarray['error_message'] = 'Problem Signing up , one of the values is empty';
    }
    else{


        if($result=mysqli_query($link,$CheckemailString)){


            if($result -> num_rows>0 ){

                $apiarray['error'] = true;
                $apiarray['error_message'] = 'Email Address alread exists!';
            }
            else{


                signup($email,$password,$name,$location,$token,$date);
                


            }

        }


        
    } 


    echo json_encode($apiarray);
//sendemailmessage($email,1,$token);

?>