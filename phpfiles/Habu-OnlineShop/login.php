<?php

require_once './connect.php';

$email = addslashes(strip_tags($_POST['email']));
$password = addslashes(strip_tags($_POST['password']));
$key = addslashes(strip_tags($_POST['key']));

$userquery = "SELECT * FROM `customer_info` WHERE email = '$email'";
$adminquery = "SELECT * FROM `admin_info` WHERE email = '$email'";

$userloginq="SELECT * FROM `customer_info` WHERE email = '$email' AND password = '$password'";
$adminloginq="SELECT * FROM `admin_info` WHERE email = '$email' AND password = '$password'";


$securitykey = "aicnaicbiecnaon12421312312xsadmaoiwjidoaj141242oakmsclncejoS1212311dijaoWJIdjSDJWIDJSKC1231231OEJAIDNIADNSJNCJENCE";

$loginarr = array();
if($key!=$securitykey)
{

$loginarr['error']=true;
$loginarr['error_message']='Access Denied';
$loginarr['type']='';

echo json_encode($loginarr);
die();

}

else {


    if($doc = mysqli_query($link,$userquery)){

        if($doc->num_rows>0)
        {

            $docu = mysqli_query($link,$userloginq);

                if($docu->num_rows>0){





                    $loginarr['error']=false;
                    $loginarr['error_message']='';
                    $loginarr['type']='customer';
                    $row=$docu->fetch_assoc();
                    $loginarr['token']=$row['token'];
                    $loginarr['name']=$row['name'];
                    $loginarr['isverified']=$row['isverified'];
                    $loginarr['CreationDate']=$row['CreationDate'];

                    $loginarr['location']=$row['location'];




                }

                else {

                    $loginarr['error']=true;
                    $loginarr['error_message']='Invalid Password';
                    $loginarr['type']='';
                }
            }


        
        else
        {
            if($doa=mysqli_query($link,$adminquery)){


                if($doa->num_rows>0)
                            {

                            $doca = mysqli_query($link,$adminloginq);

                        if($doca->num_rows>0){

                            $loginarr['error']=false;
                            $loginarr['error_message']='';
                            $loginarr['type']='admin';
                            $dow = $doca->fetch_assoc();
                            $loginarr['name'] = $dow['name'];
                            $loginarr['email'] = $dow['email'];
                            $loginarr['password'] = $dow['password'];
                        }

                        else {

                            $loginarr['error']=true;
                            $loginarr['error_message']='Invalid Password';
                            $loginarr['type']='';
                        }


                    }

                else
                {

                    $loginarr['error']=true;
                    $loginarr['error_message']="This email address doesn't Exist!";
                    $loginarr['type']='';
                }
            }
        }
    }
}

   echo json_encode($loginarr);






?>