<?php
require_once './connect.php';

if($_POST){
$sql = "INSERT INTO `customer_orders` 

(`pname`, `customer_email`, `province`, `city`, `street`, `progress`, `price`, `date`,`recipient`) 
VALUES(?,?,?,?,?,?,?,?,?)";


$pid = $_POST['pid'];
$email = $_POST['email'];
$province = $_POST['province'];
$city = $_POST['city'];
$street = $_POST['street'];
$progress = $_POST['progress'];
$price = $_POST['price'];
$date = $_POST['date'];

$letter = chr(rand(97,122));
$recipe = rand(100000000000,999999999999999) . $letter; 

$productid = $_POST['productid'];



do {

    $letter = chr(rand(97,122)) .chr(rand(97,122)) ;
$recipe = rand(100000000000,999999999999999) ."-". $letter; 
$checkrecipe = "SELECT * FROM customer_orders WHERE recipient='$recipe'";
$qo = mysqli_query($link,$checkrecipe);

$query_record = mysqli_fetch_array($qo);
    if(! $query_record) {
        break;
    }

}

while(1);






$stmt = $link->prepare($sql);
$api = array();

$stmt->bind_param("sssssssss", $pid, $email, $province,$city,$street,$progress,$price,$date,$recipe);

$decreasebyone = "UPDATE `product` SET `stock`=stock-1 WHERE `id` = '$productid'";



if($stmt->execute()){

    $api['error']=false;
    $api['error_message']='';

    if(mysqli_query($link,$decreasebyone)){

    }
    else{
        $api['error_message'] = mysqli_error($link);
    }
    
}

else{

    $api['error'] = true;
    $api['error_message'] = mysqli_error($link);
}


echo json_encode($api);

}



?>