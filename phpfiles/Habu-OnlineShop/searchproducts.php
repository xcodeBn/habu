<?php

require_once './connect.php';
header('Content-Type: application/json');



if($_GET){

//     $name = addslashes(strip_tags($_GET['name']));
// $category = addslashes(stripslashes($_GET['category']));

$name = $_GET['name'];
$category = $_GET['category'];
$arr =  array();
mysqli_set_charset($link, 'utf8');

$sql = "SELECT * FROM `product` WHERE name LIKE '%$name%' and category ='$category'";
if($do = mysqli_query($link,$sql)){

    // $arr['error'] =false;
    // $arr['error_message'] = '';
    // $custom =array('error' =>false ,'error_message' =>'');
    // $arr[] = $custom;


    while($row = $do ->fetch_assoc()){

        $arr[]=$row;
    }




}


else {

    $arr['error'] = true;
    $arr['error_message'] = mysqli_error($link);
}

echo json_encode($arr,JSON_UNESCAPED_SLASHES);
}

?>