<?php

require_once './connect.php';
$apiarray = array();


if($_POST){
$product =$_POST['id'];

$q="SELECT * FROM `review` WHERE productid = ?";

$statement  = $link ->prepare($q);
$statement ->bind_param('s',$product);


if($statement ->execute()){
$result = $statement->get_result();


while($row  =$result->fetch_assoc() ){
    $apiarray[] = $row;
}
}

else {

}

}
echo json_encode($apiarray);


?>