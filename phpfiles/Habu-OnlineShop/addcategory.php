<?php

require_once './connect.php';


if($_POST){

 
    $name = $_POST['name'];

    $image = $_POST['image'];

    $api = array();
    do {
        $random_number = mt_rand(10000,10000000000);
        $query_object = mysqli_query($link, "SELECT * FROM `category` WHERE image = $random_number");
        $query_record = mysqli_fetch_array($query_object);
        if(! $query_record) {
            break;
        }
    } while(1);

        $q1 = "INSERT INTO category (name,image) values('$name','$random_number$name.png')";
        

      

        if ($r = mysqli_query($link,$q1) ) {

            $path = "images/categoryimage/" .$random_number.$name.".png";

            file_put_contents($path,base64_decode($image));

            

            $api['error']=false;


           

        }
        
        else  {


            $api['error']=true;


        }

        echo json_encode($api);
    }





?>