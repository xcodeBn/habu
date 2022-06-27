<?php
require_once './connect.php';

if($_GET){


    $id = $_GET['email'];



    $sql = "SELECT product.id , product.image,cart.email, product.name , product.price
    FROM product
    INNER JOIN cart
    ON product.id=cart.product 
    INNER JOIN customer_info
    ON customer_info.email = '$id'";

    if($result = mysqli_query($link,$sql)){


        $api = array();

        while($row = $result->fetch_assoc()){

            $api[] = $row;
        }

        echo json_encode($api);
    }

}


?>