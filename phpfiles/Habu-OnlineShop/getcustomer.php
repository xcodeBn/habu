<?php

require_once './connect.php';

$apiarray = array();

if($_POST){
    $email = $_POST['email'];

    $sql = "SELECT name FROM `customer_info` WHERE email = ?";
    $statement = $link ->prepare($sql);
    $statement ->bind_param("s",$email);

    if($statement ->execute()){

        $result = $statement->get_result();
        while($row = $result->fetch_assoc()){

            $apiarray = $row;
        }
    }

}

echo json_encode($apiarray);

?>