$(document).ready(function () {
	Login.init();
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#login").attr("disabled", true);
				Login.doAction();
			}
		}
	});
    $.backstretch([
	     "../img/loginbg/1.jpg",
	     "../img/loginbg/2.jpg",
	     "../img/loginbg/3.jpg",
	     "../img/loginbg/4.jpg"
    ], {
        fade: 1000,
        duration: 8000
    });
});
var Login = function () {

	var handleLogin = function() {
		$('#form').validate({
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
                $('.alert-danger', $('#form')).show();
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

        $('#form input').keypress(function (e) {
            if (e.which == 13) {
                if ($('#form').validate().form()) {
                	 
                	doAction();
                }
                return false;
            }
        });
	}

	var doAction = function (){
		var params = {
			"usercode": encodeSpecialCode($("#usercode").val()), 
			"password": encodeSpecialCode($("#password").val())
		};

		$.ajax({
			type: "POST",
			url: "../user/userlogin.do?a=login",
			data: $.param(params),
			dataType: "json",
			success : function(data) {
				if(data!=null&&parseInt(data.id,10)>0){
					setCookie("login$UserCode",document.getElementById("usercode").value);
					doAuth(data);
				}
				else{
					 $('.alert-danger', $('#form')).find("span").text("用户名或密码错误。");
					 $('.alert-danger', $('#form')).show();
				}
			}
		});
	}
	
	var doAuth = function(user){
		var params = {
			"UserID": user.id
		};

		$.ajax({
			type: "POST",
			url: "../user/userlogin.do",
			data: $.param(params),
			dataType: "json",
			success : function(data) {
				var type = data.type;
				if(type != null){
					if(type == 0){//个人用户登录后
						window.location.replace("../user/BasicInfo.do?infoType=1");
					} else if(type == 1){//机构用户登录后
						window.location.replace("../org/BasicInfo.do?infoType=1");
					}
				}
			}
		});
	},
	register = function(){
		window.location.replace("../userout/userRegister.jsp");
	};
	
	var weiboInit = function(){//微博登录初始化
		WB2.anyWhere(function(W){
            W.widget.connectButton({
                id: "wb_connect_btn",	
                type:"1,5",
                callback : {
                    login:function(o){	//登录后的回调函数
                        // alert("login: " + o.screen_name)
                        weiboAfterLogin(o.screen_name);
                    },	
                    logout:function(){	//退出后的回调函数
        				//alert('logout');
                    }
                }
            });
        });
	},
	weiboAfterLogin = function(weibo){
		var keyUrl = "../user/afterlogin.do?a=weiboAfterLogin&weibo=" + weibo;
		$.get(keyUrl, function(data) {
		});
	};
	/*var validate = function(){
		var usercode = $("#usercode").val();
		if(usercode == null || usercode.length <= 0){
			$("#usercode").addClass("form-control validate[maxSize[255],funcCall[userCodeVal]]");
		}
		var password = $("#password").val();
		if(password == null || password.length <= 0){
			$("#password").addClass("form-control validate[maxSize[255],funcCall[passwordVal]]");
		}
	}*/
    return {
        //main function to initiate the module
        init: function () {
        	$("#usercode").val(getCookie("login$UserCode"));
            handleLogin();
            //weiboInit();
            $("#register").click(register);
        }
    };

}();
function userCodeVal(){
	return '请输入登录用户名！';
}
function passwordVal(){
	return '请输入登录密码！';
}