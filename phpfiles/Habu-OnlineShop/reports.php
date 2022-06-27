<?php

require_once './connect.php';
$apiarray = array();


if($_POST){


    $email = addslashes(strip_tags($_POST['email']));
    $title = addslashes(strip_tags($_POST['title']));
    // $key = addslashes(strip_tags($_POST['key']));
    $text = addslashes(strip_tags($_POST['text']));
    $date = addslashes(strip_tags($_POST['date']));




    $sql = "INSERT INTO `report`( `email`, `title`, `text`, `date`)
    VALUES(
        '$email',
        '$title',
        '$text',
        '$date'
    )";



if($result = mysqli_query($link,$sql)){


    $apiarray['error']=false;
    $apiarray['error_message']="";


}

else{


    $apiarray['error']=true;
    $apiarray['error_message']=mysqli_error($link);

}


}

else{



    $apiarray['error']=true;
    $apiarray['error_message']='No Values';

}


echo json_encode($apiarray);


?>