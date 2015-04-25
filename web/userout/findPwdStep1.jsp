<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />
<%@include file="../user/inc/csslink.inc"%>
</head>

<body>
<%@include file="../inc/header.inc"%>
	<div class="shadow"></div>
	<div class="login">
    	<input type="hidden" id="resubmitToken" value="" />
     	<div class="form" style="margin-left:200px;">
        	<img src="../userout/img/psw_step1.png" width="369" height="56" alt="找回密码第一步" />
        	<br><br>
        	 
            <form class="form-horizontal" role="form"  id="form" name="form" action="../user/user.do?a=findPwd" method="post">
           		<div class="form-group">
					<label for="code" class="col-sm-3 control-label">邮件:</label>
					<div class="col-sm-9">
						<input type="text" class="form-control validate[maxSize[255],custom[email],required]" maxlength="20" id="email" name="email" placeholder="请输入注册时的邮箱" >
					</div>
					
				</div>
           	    <div class="form-group">
		        <label for="myVal" class="col-sm-3 control-label">验证码:</label>
		        <div class="col-sm-4">
		          <input type="text" class="form-control  validate[maxSize[4],required]" maxlength="4" id="myVal" name="myVal"  >
		          
		        </div>
		        
		      </div>  
		      <div class="form-group">
		      
		        <div class="col-sm-offset-3 col-sm-6">
		        	<img id="validateCode" src="" style="vertical-align: middle; margin-right: 20px;"></img><a id="changeCode" style="cursor:pointer;" onclick="changeValPic();">换一张</a>
		           <input type="hidden" id="curVal" name="curVal" style="color: #fe6249;text-decoration: underline;cursor: pointer;" value=""/>
		        </div>
		      </div>  
                
                <div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" id="login" name="login" class="btn btn-success" >找回密码</button>
					</div>
				</div>
                
            </form>
        </div>
    </div>
    
   
	<!-- footer -->
	<!-- script -->
	<%@include file="../user/inc/script.inc"%>
	<!-- script -->
	<script type="text/javascript" src="script/findPwd.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	//初始化
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#submitLogin").attr("disabled", true);
				findPwd.submit();
			}
		}
	});
	
	changeValPic();
	
});

function changeValPic(){
	$.get("../Captcha.do?action=randomcode", function(data){
			$("#curVal").val(data);
			$("#validateCode").attr("src","../Captcha.do?action=getimage&randomcode="+data+"&rand="+Math.random());
		});
}
function checkMyVal(){
	var curVal = $("#curVal").val().toLowerCase();
	var myVal = $("#myVal").val().toLowerCase();
	  
	if(myVal != null && myVal !="" && myVal != curVal){
		
		return false;
    } else{
		
		return true;
	}
}

var findPwd = {
	submit : function(){
		//window.location.replace("findPwdStep2.jsp?email=" + encodeURI("leijuan1014@163.com") + "&emailType=" + encodeURI("http://mail.163.com"));
		 if(!checkMyVal()){
 		    var el = $("#myVal");
 			var controlGroup = el.parents('.form-group');
 			controlGroup.removeClass('has-error has-success');
 	        controlGroup.addClass('has-error');
 	        controlGroup.find("#valierr").remove();
 	        el.parent().after('<span class="help-block" id="valierr">请输入正确的验证码。</span>');
 	      
 	        return false;
		 
 	  	}
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/userlogin.do?a=findPwd", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'json',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			if(data.error != null && data.error.length > 0){
	    				 
	    				$.messager.alert('消息',data.error);
	    			} else {
	    				var email = data.email;
	    				var emailType = data.emailType;
	    				window.location.replace("sendpwd.do?email=" + encodeSpecialCode(email) + "&emailType=" + emailType);
	    			}
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	}
}
</script>
 <!-- footer -->
	<%@include file="../inc/footer.inc"%>
</body>
</html>