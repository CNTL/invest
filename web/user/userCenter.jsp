<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>个人中心</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    
</head>
<!-- END HEAD -->

<body class="login">
    <!-- BEGIN LOGO -->
    <div class="logo">
        	个人中心
        	<select onchange="change();" style="width: 300px;" id="type" name="type" class="custform-select validate[maxSize[255],required]" url="app/Data.do?type=USERTYPE" oldValue="-" pair="true">
            <option value="0">个人主页</option>
            <option value="1">照片展示</option>
            <option value="2">简介</option>
            <option value="3">作品</option>
            <option value="4">修改头像</option>
            <option value="5">实名认证</option>  
            </select>
    </div>
    <!-- END LOGO -->
 
    <!-- BEGIN LOGIN -->
    <div class="content" style="height:700px">
		<iframe id="contentFrame" name="contentFrame" src="userComplete.jsp" frameborder="0" scrolling="auto" style="height:100%;width:100%"></iframe>
    </div>
    <script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
    <script>
    	function change(){
    		var type = $("#type").val();
    		if(type == "0") {
    			 $("#contentFrame").attr("src", "userComplete.jsp");
    		} else if(type == "1") {
    			$("#contentFrame").attr("src", "userPhoto.jsp");
    			//$("#contentFrame").attr("src", "UploadFlash.jsp");
    		} else if(type == "2") {
    			$("#contentFrame").attr("src", "userIntro.jsp");
    		} else if(type == "3") {
    			$("#contentFrame").attr("src", "userWork.jsp");
    		} else if(type == "4") {
    			$("#contentFrame").attr("src", "userHeadImg.jsp");
    		} else if(type == "5") {
    			$("#contentFrame").attr("src", "userRelAuth.jsp");
    		}
    	}
    </script>
    <!-- END JAVASCRIPTS -->

</body>
<!-- END BODY -->
</html>