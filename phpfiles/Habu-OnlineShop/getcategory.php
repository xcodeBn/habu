<?php

require_once './connect.php';

$sql = "Select * from category";
$arr = array();
if($q=mysqli_query($link,$sql)){


while($row = $q->fetch_assoc()){


$arr[] = $row;

}


}

else {


    $arr['error'] = true;
    $arr['error_message'] = mysqli_error($link);
}
echo stripslashes(json_encode($arr));
?>