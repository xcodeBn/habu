<?php 
require_once './connect.php'; ?>

<html>
<body>

<form action="addproduct.php" autocomplete="on" method="post" enctype="multipart/form-data">
                


                <table border="4">
                
                <tr><td>Enter prodoct name</td> <td><input type="text" name="name"></td> </tr>
                <TR><TD>Category </TD><TD><select name=category>


							
							<?php
                            $qc = "Select * from category";
                            if($do = mysqli_query($link,$qc))
                            {

                                while($row = $do->fetch_assoc())
                                {
                                    echo "<option value=" .$row['id'] . ">" . $row['name'] ."</option>";
                                }
                            }

                            
                            
                            ?>
							</Select>
						</TD>
                <tr><td>Enter prodoct size</td> <td><input type="text" name="size"></td> </tr>
                <tr><td>Enter prodoct weight</td> <td><input type="text" name="weight"></td> </tr>
                <tr><td>Enter prodoct price</td> <td><input type="text" name="price"></td> </tr>
                <tr><td>Enter prodoct stock</td> <td><input type="text" name="stock"></td> </tr>


                
                <tr><td>Upload an image</td><td><input name="image" type="file"> </td> </tr>


                <tr><td><input type="submit" name="submit"></td></tr>

                
                
                </table>


            </form>
<?php

            if ($_POST) {

//connect to databse
include "connect.php";




$name =  mysqli_real_escape_string($link,$_POST['name']);
$weight =  mysqli_real_escape_string($link,$_POST['weight']);
$size =  mysqli_real_escape_string($link,$_POST['size']);
$category =  mysqli_real_escape_string($link,$_POST['category']);
$price =  mysqli_real_escape_string($link,$_POST['price']);
$stock =  mysqli_real_escape_string($link,$_POST['stock']);

$image =   mysqli_real_escape_string($link,$_FILES['image']['name']);
$imageError = $_FILES['image']['error'];
$imageExt = explode('.', $image);
$imageActExt = strtolower(end($imageExt));
$allowedExt = array('jpg', 'jpeg', 'png');


$username_error = "";

do {
    $random_number = mt_rand(10000,10000000000);
    $query_object = mysqli_query($link, "SELECT * FROM `product` WHERE image = $random_number");
    $query_record = mysqli_fetch_array($query_object);
    if(! $query_record) {
        break;
    }
} while(1);


 
        $q1 = "INSERT INTO product (name,weight,size,category,image,price,stock) values('$name','$weight','$size','$category','$random_number$image','$price','$stock')";
        

      echo($q1);

        if ($r = mysqli_query($link,$q1) ) {

            $target_file = "images/productimages/" . basename($random_number).$_FILES["image"]["name"];
            move_uploaded_file($_FILES["image"]["tmp_name"], $target_file);


            echo "SUCCESS";

        }
        
        else  echo $link->error;
    }



?>


</body>

</html>