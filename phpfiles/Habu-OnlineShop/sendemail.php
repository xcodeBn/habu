<?php
require_once './vendor/autoload.php';

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

$SENDER_EMAIL='xxx@gmail.com';



//Replace the xxx with your own info 


$SMTP_SERVER = 'smtp-relay.sendinblue.com';
$PORT = '587';
$SMTP_KEYNAME='Master password';
$SMTP_KEYVALUE='xxx';
$mail = new PHPMailer(TRUE);


//Import the PHPMailer class into the global namespace



//SMTP needs accurate times, and the PHP time zone MUST be set
//This should be done in your php.ini, but this is how to do it if you don't have access to that
date_default_timezone_set('Etc/UTC');



function sendemailmessage($useremail,$username,$token){


   
//Create a new PHPMailer instance
$mail = new PHPMailer();
//Tell PHPMailer to use SMTP
$mail->isSMTP();
//Enable SMTP debugging
//SMTP::DEBUG_OFF = off (for production use)
//SMTP::DEBUG_CLIENT = client messages
//SMTP::DEBUG_SERVER = client and server messages

//Set the hostname of the mail server
$mail->Host = 'smtp-relay.sendinblue.com';
//Set the SMTP port number - likely to be 25, 465 or 587
$mail->Port = 587;
//Whether to use SMTP authentication
$mail->SMTPAuth = true;
//Username to use for SMTP authentication
$mail->Username = 'xxx@gmail.com';
//Password to use for SMTP authentication
$mail->Password = 'xxx';


//Set who the message is to be sent from
$mail->setFrom('HabuOnlineStore@HassanBazzoun.com', 'Habu');
//Set an alternative reply-to address

//Set who the message is to be sent to

$mail->addAddress($useremail, "$username");
//Set the subject line
$mail->Subject = 'Your Habu account verification code';
$mail->Body=("Hello $username, your Habu Account verification code is: $token");

//Read an HTML message body from an external file, convert referenced images to embedded,
//convert HTML into a basic plain-text alternative body
//$mail->msgHTML(file_get_contents("verificationemailcontect.php?token=$token"),'');
//Replace the plain text body with one created manually
//$mail->AltBody = 'This is a plain-text message body';
//Attach an image file

//send the message, check for errors
if (!$mail->send()) {
     $mailarr['error'] = true ;
     $mailarr['error_message'] ='Mailer Error: ' . $mail->ErrorInfo;

     echo json_encode($mailarr);
     
} 

else {
    
    
}


}?>