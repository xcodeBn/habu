<?php


require_once './connect.php';

if($_POST){


    $id = $_POST['id'];

    $name =$_POST['name'];
   $image = $_POST['image'];

   do {
    $random_number = mt_rand(10000,10000000000);
    $query_object = mysqli_query($link, "SELECT * FROM `category` WHERE image = $random_number");
    $query_record = mysqli_fetch_array($query_object);
    if(! $query_record) {
        break;
    }
} while(1);


$sql = "UPDATE `category` SET `image`='$random_number$name.png' WHERE id='$id'";
if($do=mysqli_query($link,$sql)){

    // $target_file = "images/categoryimage/" . basename($_FILES["image"]["name"]);
    //         move_uploaded_file($_FILES["image"]["tmp_name"], $target_file);

    $path = "images/categoryimage/" .$random_number.$name.".png";
    file_put_contents($path,base64_decode($image));


            echo "Successs";
}

else{
    echo "fail";
}


}




?>