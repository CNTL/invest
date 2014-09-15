var Login = function () {

	var handleLogin = function() {
		$('.login-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                }
	            },

	            messages: {
	                username: {
	                    required: "请输入用户名."
	                },
	                password: {
	                    required: "请输入密码."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                $('.alert-danger', $('.login-form')).show();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	              
	                doAction();
	            }
	        });

	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                	 
	                	doAction();
	                }
	                return false;
	            }
	        });
	}

	var doAction = function (){
		 
		var keyUrl = "syslogin.do?a=login&usercode="+$("#usercode").val()+"&password="+$("#password").val();
		
		$.getJSON(keyUrl, function(data) {
			
			if(data!=null&&parseInt(data.id,10)>0){
				doAuth(data);
				
			}
			else{
				 $('.alert-danger', $('.login-form')).find("span").text("用户名或密码错误。");
				 $('.alert-danger', $('.login-form')).show();
			}
		});
	}
	
	var doAuth = function(sysUser){
		var keyUrl = "syslogin.do?UserID="+sysUser.id+"&UserName="+sysUser.username+"&UserCode="+sysUser.code+"&UserPassword="+sysUser.pwd;
		$.get(keyUrl, function(data) {
			window.location.replace("main.jsp");
		});
		
	}
    
    return {
        //main function to initiate the module
        init: function () {
        	
            handleLogin();
          
        }

    };

}();