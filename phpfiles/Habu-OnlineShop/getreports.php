<?php

require_once './connect.php';
$api = array();
$sql = "SELECT report.id,report.title,report.text,report.date,customer_info.name 
FROM report
INNER JOIN customer_info
ON customer_info.email = report.email";

if($do = mysqli_query($link,$sql)){



    while($row = $do->fetch_assoc()){

        $api[] = $row ;
    }

    echo json_encode($api);
}

?>