function registerSubmit(){
	//1.旧密码是否正确，须校验 2.新密码校验 3.密码加密 4.写入数据库
	//2.新密码是否一致
	//3.对密码进行加密,写入数据库
	$.ajax({
        type:"POST", //请求方式  
        url:"../user/userlogin.do?a=create", //请求路径  
        cache: false,     
        data:$('#form').serialize(),  //传参       
        dataType: 'text',   //返回值类型  
        success:function(data){
    		if(data != null && data == 'ok'){
    			$.messager.popup("注册成功！正在跳转到登录页面。",login);
    		} else {
    			 
    			$.messager.alert("注册失败。");
    		}
        }  
    });
	
	 
}
function login(){
	parent.location.href = '../user/loginMain.do';
}

function checkpassword(){
	var password1 = $("#password1").val();
	var password2 = $("#password2").val();
	//密码限制6位以上
	if(password1 == password2){
		return true;
	}
	else{
		return false;
	}
}

function checkUser(){
	
   $.getJSON("../user/userlogin.do?a=checkuser&code="+$("#code").val()+"&mail="+$("#email").val(), function(json){
	   alert("Data Loaded: " + json);
	});
}

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

$(document).ready(function () {
	
	changeValPic();
	$("#myVal").change(function(){
		//checkMyVal();
	});
	$('#roleSelect>li').click(function () {
        $('#roleSelect>li').removeClass('current');
        $(this).addClass('current');
        $('#type').val($(this).attr('data'));
    });
	$("#form").validation();
    $("#login").on('click', function (event) {
      if ($("#form").valid(this, '填写信息不完整。') == false) {
          return false;
      }
      else{
    	  var isready = true;
    	  
    	  if(!checkpassword()){
    		    var el = $("#password2");
	  			var controlGroup = el.parents('.form-group');
	  			controlGroup.removeClass('has-error has-success');
	  	        controlGroup.addClass('has-error');
	  	        controlGroup.find("#valierr").remove();
	  	        el.parent().after('<span class="help-block" id="valierr">两次输入的密码不一致。</span>');
	  	        isready = false;
    	  }
    	  if(!checkMyVal()){
    		    var el = $("#myVal");
    			var controlGroup = el.parents('.form-group');
    			controlGroup.removeClass('has-error has-success');
    	        controlGroup.addClass('has-error');
    	        controlGroup.find("#valierr").remove();
    	        el.parent().after('<span class="help-block" id="valierr">请输入正确的验证码。</span>');
    	        isready = false;
    	  }
    	  
    	  if(isready){
    		  //registerSubmit();
    		 
    		  $.ajax({
  		        type:"POST", //请求方式  
  		        url:"../user/userlogin.do?a=checkuser&code="+$("#code").val()+"&mail="+$("#email").val(), //请求路径  
  		        cache: false,  
  		        async:false,
  		        dataType: 'json',   //返回值类型  
  		        success:function(data){
  		    		 
	    			var ret = true;
	    			if(data.code!=""){
	    				ret = false;
	    				var el = $("#code");
  		      			var controlGroup = el.parents('.form-group');
  		      			controlGroup.removeClass('has-error has-success');
  		      	        controlGroup.addClass('has-error');
  		      	        controlGroup.find("#valierr").remove();
  		      	        el.parent().after('<span class="help-block" id="valierr">'+data.code+'</span>');
	    			}
	    			if(data.mail!=""){
	    				ret = false;
	    				var el = $("#email");
  		      			var controlGroup = el.parents('.form-group');
  		      			controlGroup.removeClass('has-error has-success');
  		      	        controlGroup.addClass('has-error');
  		      	        controlGroup.find("#valierr").remove();
  		      	        el.parent().after('<span class="help-block" id="valierr">'+data.mail+'</span>');
	    			}
	    			if(!ret){
	    				return ret;
	    			}else{
	    				 var data = {
	    						 code:$("#code").val(),
	    						 email:$("#email").val(),
	    						 type:$("#type").val(),
	    						 password:$("#password1").val()
	    				 };
	    	    		  $.ajax({
	    	    		        type:"POST", //请求方式  
	    	    		        url:"../user/userlogin.do?a=create", //请求路径  
	    	    		        cache: false,     
	    	    		        async:false,
	    	    		        data:data,  //传参       
	    	    		        dataType: 'text',   //返回值类型  
	    	    		        success:function(data){
	    	    		    		if(data != null && data == 'ok'){
	    	    		    			$.messager.popup("注册成功！正在跳转到登录页面。",login);
	    	    		    		} else {
	    	    		    			 
	    	    		    			$.messager.alert("注册失败。");
	    	    		    		}
	    	    		    		return false;
	    	    		        }  
	    	    		    });
	    			}
  		    			
  		    		 
  		        }  
  		    });

    		  return false;
    	  }
    	  else{
    		  return isready;
    	  }
    	  
    	  
      }
  });
});