<?php
require_once './connect.php';

$sql = "SELECT SUM(price)\n"

    . "FROM customer_orders\n"

    . "WHERE 1";

    $row = mysqli_query($link,$sql);
    
    
    
        $arr = array();
        while($d = $row->fetch_assoc()){

            $arr[] = $d;
        }
    
   echo json_encode($arr);


?>