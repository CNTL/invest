<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>实名认证</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    
 	<link type="text/css" rel="stylesheet" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
    <link href="../css/adminlogin.css" rel="stylesheet" type="text/css" />
    <link href="../css/components.css" rel="stylesheet" type="text/css" />
    
    <!-- END THEME STYLES -->
    <link rel="shortcut icon" href="../img/favicon/favicon.ico" type="image/x-icon">
    <link rel="icon" href="../img/favicon/favicon.ico" type="image/x-icon">
</head>
<!-- END HEAD -->

<body class="login">
    <!-- BEGIN LOGO -->
    <div class="logo">
        	实名认证
    </div>
    <!-- END LOGO -->
 
    <!-- BEGIN LOGIN -->
    <div class="content">
        <!-- BEGIN LOGIN FORM -->
        <form id="form" name="form" method="post" action="user.do?a=relAuth">
        	<div id="defDiv">
	        	<div class="form-group">
	                <label for="name"><i style="color:red;">*&nbsp;&nbsp;</i>卡号</label>
	                <input class="form-control validate[required]" type="text" autocomplete="off" placeholder="请输入卡号" name="bankNums" />
	            </div>
	            <div class="form-group">
	                <label for="code"><i style="color:red;">*&nbsp;&nbsp;</i>开户行</label>
	                <input class="form-control validate[required]" type="text" autocomplete="off" placeholder="请输入开户行" name="openingBanks" />
	            </div>
	            <img id="img" src="../img/add.png" style="width:30px;height:30px;" onclick="addBankCard();"/>
            </div>
       
	         <div class="form-actions">
	        		<button id="btnCancel" type="button" onclick="window.close();" class="btn blue pull-right">
	                	<i>关闭</i>
	             </button>
	             <button id="btnSave" type="submit" class="btn blue pull-right">
	                	<i onclick="">保存</i>
	             </button>
	         </div>
          </form>
        <!-- END LOGIN FORM -->


    </div>
    <!-- END LOGIN -->
    <!-- BEGIN COPYRIGHT -->
    <!-- END COPYRIGHT -->
    <script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="../js/plugin/backstretch/jquery.backstretch.min.js"></script>
    <script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
 	<script type="text/javascript" src="script/userRegister.js"></script>
 
    <script>
        function addBankCard(){
	        var defBankCard = "<div>" + $("#defDiv").html() + 
	        	'<img id="img" src="../img/del.png" style="width:30px;height:30px;" onclick="delBankCard(this);"/>' +'</div>';
        	$("#defDiv").after(defBankCard);
        }
        
        function delBankCard(delDom){
        	$(delDom).parent().remove();
        }
    </script>
    <!-- END JAVASCRIPTS -->

</body>
<!-- END BODY -->
</html>