<html>
<body>

<form action="addgategory.php" autocomplete="off" method="post" enctype="multipart/form-data">
                

                <label for="fname"><b>First Name:</b></label>
                <input name="fname"  placeholder="Enter First Name" type="text"><BR>
                <span style="color:red">
                    <p id="firstname_error"></p>
                </span>


                


                <input name="size" type="hidden" value="1000000">
                <label for="image"><b>Upload a profile image: </b></label>
                <input name="image" type="file">
                <span style="color:red">
                    <p id="image_error"></p>
                </span>



                <input class="registerbtn" type="submit" value="Sign UP">
                <span style="color:green">
                    <p id="registration_success"></p>
                </span>



            </form>
<?php

            if ($_POST) {

//connect to databse
include "connect.php";




$fname =  mysqli_real_escape_string($link,$_POST['fname']);


$image =   mysqli_real_escape_string($link,$_FILES['image']['name']);
$imageError = $_FILES['image']['error'];
$imageExt = explode('.', $image);
$imageActExt = strtolower(end($imageExt));
$allowedExt = array('jpg', 'jpeg', 'png');


$username_error = "";


 
        $q1 = "INSERT INTO category (name,image) values('$fname','$image')";
        

      echo($q1);

        if ($r = mysqli_query($link,$q1) ) {

            $target_file = "images/categoryimage/" . basename($_FILES["image"]["name"]);
            move_uploaded_file($_FILES["image"]["tmp_name"], $target_file);


            echo "SUCCESSS";

        }
        
        else  echo $link->error;
    }



?>


</body>

</html>