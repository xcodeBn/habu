<!DOCTYPE html>
    <html lang="en">

    
    <head>
      <meta charset="UTF-8">
      <title>Test mail</title>
      <?php $token= $_GET['token'];?>
      <style>
      
      
        .wrapper{
    width: 100%;
    max-width: 700px;
    margin: 20px auto;
    border-radius: 0px;
    box-shadow: 0px 1px 6px rgba(255, 0, 157, 0.2);
    box-sizing: border-box;
    padding: 0 0 50px;
    overflow: hidden;
    border: 1px solid rgb(80, 0, 24);
    font-size: larger;
    align-items: center;  
    padding-left: 20px;
    background-color: rgb(253, 229, 243);
    border-radius: 30px;
}
        body{
         
            font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
        }
        

        #logo{

            width: auto;
            height: 100px;
            padding-left: 450px;
            
            
        }

        p{

            padding-top: 20px;
        }
        .tokenwrapper{
width: fit-content;
align-self: center ;
text-align: center;
margin:auto;
margin-top: 100px;
            border-radius: 6px;
    box-shadow: 0px 1px 6px rgba(255, 0, 157, 0.2);
    box-sizing: border-box;
    background-color: rgb(255, 0, 98);
        }

        #title{

            font-size: 30px;
            font-style:inherit;
            font-weight: bolder;
            border: 2px 2px lightpink;
            
        }

        .bottom{

          border: 2px 2px lavender;
          
        }

        
      </style>
    </head>

    <body>

        
      <div class="wrapper">
        
      
        <p id="title">Thank you for signing up for <b style="color: #ff0077;" >Habu</b></p>
        <h2>Your verification code is:</h2>

        <div class="tokenwrapper">
        <h1 style="letter-spacing:20px;margin:21px auto"><?php 
        
        echo $token;?></h1></div>
        <img id="logo" src="./images/HabuSystem/HabuLogo.png">

      <div class="bottom"><p>Contact us: xxx@gmail.com</p></div>
        
      </div>
    </body>

    </html>;