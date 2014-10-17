var userset = {
	change : function(obj) {
		var url;
		if(obj.id=='infoEdit'){
			url = "userComplete.jsp";
		}else if(obj.id=='pwsEdit'){
			url = "userPwdChange.jsp";
		}else if(obj.id=='headEdit'){
			url = "userHeadImg.jsp";
		} else if(obj.id=='relAuth'){
			url = "userRelAuth.jsp";
		} else if(obj.id=='photo'){
			url = "userPhoto.jsp";
		} else if(obj.id=='video'){
			url = "userVideo.jsp";
		}
		userset.setClass(obj);
		$("#main").attr("src",url);
	},
	setClass : function (obj){
		$(obj).parent().parent().find("li").each(function(){
		    $(this).removeClass("select");
		});
		$(obj).parent().addClass("select");
	}
};