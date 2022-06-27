<?php

require_once './connect.php';


$arr = array();
if($_GET){

    $id=$_GET['id'];
$sql = "Select * from product where category = '$id'";

if($q=mysqli_query($link,$sql)){


while($row = $q->fetch_assoc()){


$arr[] = $row;

}


}

else {


    $arr['error'] = true;
    $arr['error_message'] = mysqli_error($link);
}

}
echo stripslashes(json_encode($arr));
?>