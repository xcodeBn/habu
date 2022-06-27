<?php

require_once './connect.php';


if($_POST){

 
    $name = $_POST['name'];

    $size = $_POST['size'];
    $weight = $_POST['weight'];
    $stock = $_POST['stock'];
    $price = $_POST['price'];
    $image = $_POST['image'];
    $category = $_POST['category'];

    $api = array();
    do {
        $random_number = mt_rand(10000,10000000000);
        $query_object = mysqli_query($link, "SELECT * FROM `category` WHERE image = $random_number");
        $query_record = mysqli_fetch_array($query_object);
        if(! $query_record) {
            break;
        }
    } while(1);

        $q1 = "INSERT INTO category (name,image) values('$name','$random_number.png')";
        

        $q2 = "INSERT INTO `product`( `name`, `weight`, `size`, `category`, `image`, `price`, `stock`) VALUES ('$name','$weight','$size','$category','$random_number.png','$price','$stock')";
      

        if ($r = mysqli_query($link,$q2) ) {

            $path = "images/productimages/" .$random_number.".png";

            file_put_contents($path,base64_decode($image));

            

            $api['error']=false;


           

        }
        
        else  {


            $api['error']=true;


        }

        echo json_encode($api);
    }





?>